package controllers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import model.DeadlineNoteEntity;
import model.LocalDataBase;
import model.MeetingNoteEntity;
import model.NoteEntity;
import model.OrdinaryNoteEntity;
import model.ShoppingNoteEntity;
import receivers.AlarmReceiverDeadlineNote;
import services.SynchronizationService;

/**
 * Created by Esraa on 13-Mar-16.
 */
public class NoteController {

    static final public String ordinaryNote = "Ordinary";
    static final public String meetingNote = "Meeting";
    static final public String deadlineNote = "Deadline";
    static final public String shoppingNote = "Shopping";

    public NoteController() {
    }

    public void addOrdinaryNote(String noteContent, String priority) {
        UserController userController = UserController.getInstance();
        String userID = String.valueOf(userController.getCurrentUserID());

        try {
            String res = new CallWebService().execute("http://5-dot-secondhelloworld-1221.appspot.com/restNotes/addOrdinaryNoteService",
                    noteContent, userID, "addOrdinaryNoteService").get();

            JSONObject object = new JSONObject(res);
            if (!object.has("Status") || object.getString("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
                return;
            } else {
                addOrdinaryNoteInLoacalDB(noteContent, priority, true, Long.valueOf(object.get("noteid").toString()));
                Toast.makeText(MyApplication.getAppContext(), "Ordinary note id is " + object.get("noteid").toString(), Toast.LENGTH_LONG).show();


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addShoppingNote(String productToBuy, String priority, String category) {

        UserController userController = UserController.getInstance();
        String userID = String.valueOf(userController.getCurrentUserID());

        try {
            String res = new CallWebService().execute("http://5-dot-secondhelloworld-1221.appspot.com/restNotes/addShoppingNoteService",
                    productToBuy, category, userID, "addShoppingNoteService").get();
            JSONObject object = new JSONObject(res);

            if (!object.has("Status") || object.getString("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
                return;
            } else {
                AddShoppingNoteInLocalDB(productToBuy, priority, category, true, Long.valueOf(object.get("noteid").toString()));

                Toast.makeText(MyApplication.getAppContext(), "Shopping note is added successfully", Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int addDeadlineNote(String title, String priority, String deadlinedate_time, int progressValue) {
        UserController userController = UserController.getInstance();
        String userID = String.valueOf(userController.getCurrentUserID());

        int id = -1;

        try {
            String res = new CallWebService().execute("http://5-dot-secondhelloworld-1221.appspot.com/restNotes/addDeadLineNoteService",
                    title, deadlinedate_time, String.valueOf(progressValue), userID, "addDeadLineNoteService").get();
            JSONObject object = new JSONObject(res);
            if (!object.has("Status") || object.getString("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
                return -1;
            } else {
                id = addDeadlineNoteInLoacalDB(title, priority, deadlinedate_time, progressValue, true, Long.valueOf(object.get("noteid").toString()));

                Toast.makeText(MyApplication.getAppContext(), "deadline note is added successfully", Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int addMeetingNote(String title, String place, String agenda, String date, String priority, String time) {
        UserController userController = UserController.getInstance();
        String userID = String.valueOf(userController.getCurrentUserID());

        int id = -1;
        try {
            //http://fci-gp-intelligent-to-do.appspot.com/rest/
            String res = new CallWebService().execute("http://5-dot-secondhelloworld-1221.appspot.com/restNotes/addMeetingNoteService",
                    title, place, agenda, date, time, userID, priority, "addMeetingNoteService").get();
            JSONObject object = new JSONObject(res);
            if (!object.has("Status") || object.getString("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
                return -1;
            } else {
                id = addMeetingNoteInLoacalDB(title, place, agenda, date, priority, time, true, Long.valueOf(object.get("noteid").toString()));
                Toast.makeText(MyApplication.getAppContext(), "Meeting note is added successfully", Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return id;
    }

    public void Syncroinzation(String NotSyncNotes) {
        try {
            String res = new CallWebService().execute("http://5-dot-secondhelloworld-1221.appspot.com/restNotes/synchroinzationService",
                    NotSyncNotes, "synchroinzationService").get();

            Log.i("Resssultsynchronize", res);

            JSONArray jsonArray = new JSONArray(res);
            LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Log.i("syncType=", jsonObject.getString("syncType").toString());
                Log.i("noteid=", jsonObject.getString("noteid"));

                if (jsonObject.getString("syncType").equals("Added")) {
                    long serverid = Long.parseLong(jsonObject.getString("servernoteid"));
                    int id = Integer.parseInt(jsonObject.getString("noteid"));
                    //setAddedToOne
                    localDataBase.SycAdd(serverid, id);

                } else if (jsonObject.getString("syncType").equals("Updated")) {
                    int id = Integer.parseInt(jsonObject.getString("noteid"));
                    //ResetUpdate
                    localDataBase.ResetUpdate(id);

                } else if (jsonObject.getString("syncType").equals("Delete")) {
                    int id = Integer.parseInt(jsonObject.getString("noteid"));
                    //Delete
                    localDataBase.DeleteNotePermanently(id);
                } else if (jsonObject.getString("syncType").equals("Done")) {
                    int id = Integer.parseInt(jsonObject.getString("noteid"));
                    //Delete
                    localDataBase.SycDone(id);
                }
                //Log.i("Note_status", object.getString("status"));
            }
            Log.i("Note_status", "Sync done");

            // Toast.makeText(MyApplication.getAppContext(),"Sync done ",Toast.LENGTH_LONG).show();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addOrdinaryNoteInLoacalDB(String notecontent, String Priority, boolean issync, long serverNoteId) {

        boolean isDone = false;
        boolean isdeleted = false;
        boolean isTextCategorized = false;


        OrdinaryNoteEntity noteEntity = new OrdinaryNoteEntity(ordinaryNote, Priority,
                getCurrentDate(), isDone, isdeleted, isTextCategorized, notecontent, issync);
        noteEntity.setServernoteId(serverNoteId);
        noteEntity.setUserId(UserController.getCurrentUserID());
        Log.i("Servernoteid=", String.valueOf(serverNoteId));
        LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
        long id = localDataBase.InsertOrdinaryNote(noteEntity);
        if (id == -1) {
            Toast.makeText(MyApplication.getAppContext(), " Error while inserting ", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(MyApplication.getAppContext(), " Inserted done note id is " + String.valueOf(id), Toast.LENGTH_LONG).show();
        }
    }

    public void AddShoppingNoteInLocalDB(String productToBuy, String priority, String category, boolean isadded, long serverNoteId) {

        boolean isDone = false;
        boolean isDeleted = false;
        boolean isTextCategorized = false;

        ShoppingNoteEntity shoppingNoteEntity = new ShoppingNoteEntity(shoppingNote, priority,
                getCurrentDate(), isDone, isDeleted, isTextCategorized, isadded, productToBuy, category);
        shoppingNoteEntity.setServernoteId(serverNoteId);
        shoppingNoteEntity.setUserId(UserController.getCurrentUserID());

        LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
        long id = localDataBase.InsertShoppingNote(shoppingNoteEntity);

        if (id == -1) {
            Toast.makeText(MyApplication.getAppContext(), " Error while inserting ", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(MyApplication.getAppContext(), " Inserted done note id is " + String.valueOf(id), Toast.LENGTH_LONG).show();
        }
    }

    public int addMeetingNoteInLoacalDB(String title, String place, String agenda, String meetingNoteDate,
                                        String Priority, String estimatedTransportTime, boolean issync, long serverNoteId) {

        boolean isDone = false;
        boolean isdeleted = false;
        boolean isTextCategorized = false;

        MeetingNoteEntity noteEntity = new MeetingNoteEntity(meetingNote, Priority,
                getCurrentDate(), isDone, isdeleted, isTextCategorized, issync, title, place, agenda,
                Timestamp.valueOf(meetingNoteDate), Time.valueOf(estimatedTransportTime));
        noteEntity.setServernoteId(serverNoteId);
        noteEntity.setUserId(UserController.getCurrentUserID());

        LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
        long id = localDataBase.InsertMeetingNote(noteEntity);
        if (id == -1) {
            Toast.makeText(MyApplication.getAppContext(), " Error while inserting ", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(MyApplication.getAppContext(), " Inserted done note id is " + String.valueOf(id), Toast.LENGTH_LONG).show();
        }

        int idInt = ((Long) id).intValue();
        Log.i("HELLO MeetingID", String.valueOf(idInt));

        return idInt;
    }

    public int addDeadlineNoteInLoacalDB(String title, String priority, String deadlinedate_time, int progressValue, boolean issync, long serverNoteId) {

        boolean isDone = false;
        boolean isdeleted = false;
        boolean isTextCategorized = false;

        DeadlineNoteEntity noteEntity = new DeadlineNoteEntity(deadlineNote,
                getCurrentDate(), isDone, isdeleted, isTextCategorized, issync, progressValue, title, Timestamp.valueOf(deadlinedate_time), priority);
        noteEntity.setServernoteId(serverNoteId);
        noteEntity.setUserId(UserController.getCurrentUserID());

        LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
        long id = localDataBase.InsertDeadlineNote(noteEntity);
        if (id == -1) {
            Toast.makeText(MyApplication.getAppContext(), " Error while inserting ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MyApplication.getAppContext(), " Inserted done note id is " + String.valueOf(id), Toast.LENGTH_LONG).show();
        }

        int idInt = ((Long) id).intValue();
        Log.i("HELLO DeadlineID", String.valueOf(idInt));

        return idInt;
    }

    private Timestamp getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTimeStamp = dateFormat.format(new Date()); // Find todays date
        return Timestamp.valueOf(currentTimeStamp);
    }

    public ArrayList<NoteEntity> ShowAllNotes(String HistoryORCurrent) {

        LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
        Cursor res = null;
        Log.i("getCurrentUserID()", String.valueOf(UserController.getCurrentUserID()));

        if (HistoryORCurrent.equals("Current")) {
            res = localDataBase.SelectCurrentNotes(UserController.getCurrentUserID());
        } else if (HistoryORCurrent.equals("History")) {
            res = localDataBase.SelectHistoryNotes(UserController.getCurrentUserID());
        }
        Log.i("Cursorres", res.toString());

        ArrayList<NoteEntity> notes = new ArrayList<NoteEntity>();
        if (!res.moveToFirst()) {
            return notes;
        }
        String noteType;
        res.moveToFirst();
        do {
            noteType = res.getString(res.getColumnIndex("noteType")); // note type
            Log.i("noteType", noteType);
            if (noteType.equals(meetingNote)) {
                int id;
                String meetingTitle, Priority;
                meetingTitle = res.getString(res.getColumnIndex("meetingTitle"));
                Priority = res.getString(res.getColumnIndex("Priority"));
                id = res.getInt(res.getColumnIndex("localnoteId"));
                MeetingNoteEntity note = new MeetingNoteEntity(meetingNote, Priority, meetingTitle, id);
                notes.add(note);
            } else if (noteType.equals(deadlineNote)) {
                int id;
                String deadLineTitle, Priority;
                deadLineTitle = res.getString(res.getColumnIndex("deadLineTitle"));
                Priority = res.getString(res.getColumnIndex("Priority"));
                id = res.getInt(res.getColumnIndex("localnoteId"));
                DeadlineNoteEntity note = new DeadlineNoteEntity(deadlineNote, Priority, deadLineTitle, id);
                notes.add(note);


            } else if (noteType.equals(shoppingNote)) {
                int id;
                String productToBuy, Priority;
                productToBuy = res.getString(res.getColumnIndex("productToBuy"));
                Priority = res.getString(res.getColumnIndex("Priority"));
                id = res.getInt(res.getColumnIndex("localnoteId"));
                ShoppingNoteEntity note = new ShoppingNoteEntity(shoppingNote, Priority, productToBuy, id);
                notes.add(note);

            } else if (noteType.equals(ordinaryNote)) {
                String noteContent, Priority;
                int id;
                noteContent = res.getString(res.getColumnIndex("noteContent"));
                Priority = res.getString(res.getColumnIndex("Priority"));
                id = res.getInt(res.getColumnIndex("localnoteId"));
                OrdinaryNoteEntity note = new OrdinaryNoteEntity(ordinaryNote, Priority, noteContent, id);
                notes.add(note);
            }
        } while (res.moveToNext());
        res.close();
        Log.i("Notesssss", notes.toString());
        return notes;
    }

    public void DeleteNoteInLocalDB(int noteid) {
        LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
        localDataBase.DeleteNote(noteid);
        Cursor cur = localDataBase.GetAlarmByNoteId(noteid);
        if (!cur.moveToFirst())
            return;
        cur.moveToFirst();
        do {
            int alarmID = cur.getInt(cur.getColumnIndex("alarmID"));
            cancelAlarm(alarmID);
        } while (cur.moveToNext());

        cur.close();
        localDataBase.DeleteNoteAlarmPermanentlyByNoteID(noteid);

        Toast.makeText(MyApplication.getAppContext(), " note Deleted  ", Toast.LENGTH_LONG).show();

    }

    private void cancelAlarm(int alarmID) {
        Intent intent = new Intent(MyApplication.getAppContext(), AlarmReceiverDeadlineNote.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getAppContext(), alarmID, intent, 0);
        AlarmManager alarmManager = (AlarmManager) MyApplication.getAppContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }

    public String UpdateCreationDateAndIsDone(long noteid) {
        try {
            String res = new CallWebService().execute("http://5-dot-secondhelloworld-1221.appspot.com/restNotes/updateCreationDateAndIsDoneService",
                    String.valueOf(noteid), "updateCreationDateAndIsDoneService").get();
            JSONObject object = new JSONObject(res);

            if (!object.has("Status") || object.getString("Status").equals("Failed")) {
                Toast.makeText(MyApplication.getAppContext(), "Error occured", Toast.LENGTH_LONG).show();
                return "Failed";
            } else {
                Toast.makeText(MyApplication.getAppContext(), "Notes added successfully", Toast.LENGTH_LONG).show();
                return "Done";
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Failed";
    }

    ///////

    public NoteEntity GetNoteDetails(int noteId) {
        LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
        Cursor res = localDataBase.GetNoteById(noteId);
        NoteEntity note = null;
        if (res.getCount() == 0)
            return null;

        res.moveToFirst();
        String noteType, Priority;
        boolean isDone, isDeleted, isTextcat, isAdded;
        Timestamp creationDate;
        int id;
        noteType = res.getString(res.getColumnIndex("noteType")); // note type
        Log.i("noteType", noteType);
        if (noteType.equals(meetingNote)) {
            String meetingTitle;
            meetingTitle = res.getString(res.getColumnIndex("meetingTitle"));
            Priority = res.getString(res.getColumnIndex("Priority"));
            id = res.getInt(res.getColumnIndex("localnoteId"));
            creationDate = Timestamp.valueOf(res.getString(res.getColumnIndex("creationDate")));
            isDone = IntToboolean(res.getInt(res.getColumnIndex("isDone")));
            isDeleted = IntToboolean(res.getInt(res.getColumnIndex("isDeleted")));
            isTextcat = IntToboolean(res.getInt(res.getColumnIndex("isTextCategorized")));
            Timestamp MeetingDate = Timestamp.valueOf(res.getString(res.getColumnIndex("meetingNoteDate")));
            Time EstimatedTime = Time.valueOf(res.getString(res.getColumnIndex("estimatedTransportTime")));
            String place = res.getString(res.getColumnIndex("meetingPlace"));
            String agenda = res.getString(res.getColumnIndex("meetingAgenda"));
            isAdded = IntToboolean(res.getInt(res.getColumnIndex("isAdded")));
            note = new MeetingNoteEntity(meetingNote, Priority, creationDate, isDone, isDeleted, isTextcat, isAdded
                    , meetingTitle, place, agenda, MeetingDate, EstimatedTime);
            note.setNoteId(id);

        } else if (noteType.equals(deadlineNote)) {
            String deadLineTitle;
            int progressprecentage;
            Timestamp deadlineDate;
            deadLineTitle = res.getString(res.getColumnIndex("deadLineTitle"));
            Priority = res.getString(res.getColumnIndex("Priority"));
            id = res.getInt(res.getColumnIndex("localnoteId"));
            creationDate = Timestamp.valueOf(res.getString(res.getColumnIndex("creationDate")));
            isDone = IntToboolean(res.getInt(res.getColumnIndex("isDone")));
            isDeleted = IntToboolean(res.getInt(res.getColumnIndex("isDeleted")));
            isTextcat = IntToboolean(res.getInt(res.getColumnIndex("isTextCategorized")));
            isAdded = IntToboolean(res.getInt(res.getColumnIndex("isAdded")));
            progressprecentage = res.getInt(res.getColumnIndex("progressPercentage"));
            deadlineDate = Timestamp.valueOf(res.getString(res.getColumnIndex("deadLineDate")));
            note = new DeadlineNoteEntity(deadlineNote, creationDate, isDone, isDeleted, isTextcat, isAdded, progressprecentage,
                    deadLineTitle, deadlineDate, Priority);
            note.setNoteId(id);


        } else if (noteType.equals(shoppingNote)) {

            String productToBuy, Category;
            productToBuy = res.getString(res.getColumnIndex("productToBuy"));
            Priority = res.getString(res.getColumnIndex("Priority"));
            id = res.getInt(res.getColumnIndex("localnoteId"));
            creationDate = Timestamp.valueOf(res.getString(res.getColumnIndex("creationDate")));
            isDone = IntToboolean(res.getInt(res.getColumnIndex("isDone")));
            isDeleted = IntToboolean(res.getInt(res.getColumnIndex("isDeleted")));
            isTextcat = IntToboolean(res.getInt(res.getColumnIndex("isTextCategorized")));
            isAdded = IntToboolean(res.getInt(res.getColumnIndex("isAdded")));
            Category = res.getString(res.getColumnIndex("productCategory"));
            note = new ShoppingNoteEntity(shoppingNote, Priority, creationDate, isDone, isDeleted, isTextcat, isAdded, productToBuy, Category);
            note.setNoteId(id);
        } else if (noteType.equals(ordinaryNote)) {
            String noteContent;
            noteContent = res.getString(res.getColumnIndex("noteContent"));
            Priority = res.getString(res.getColumnIndex("Priority"));
            id = res.getInt(res.getColumnIndex("localnoteId"));
            creationDate = Timestamp.valueOf(res.getString(res.getColumnIndex("creationDate")));
            isDone = IntToboolean(res.getInt(res.getColumnIndex("isDone")));
            isDeleted = IntToboolean(res.getInt(res.getColumnIndex("isDeleted")));
            isTextcat = IntToboolean(res.getInt(res.getColumnIndex("isTextCategorized")));
            isAdded = IntToboolean(res.getInt(res.getColumnIndex("isAdded")));
            note = new OrdinaryNoteEntity(ordinaryNote, Priority, creationDate, isDone, isDeleted, isTextcat, noteContent, isAdded);
            note.setNoteId(id);
        }
        res.close();
        return note;
    }

    public void DoneNoteInLocalDB(int noteid){
        LocalDataBase localDataBase =new LocalDataBase(MyApplication.getAppContext());
        localDataBase.SetIsDone(noteid);
        Toast.makeText(MyApplication.getAppContext(), "the  note will be in history ", Toast.LENGTH_LONG).show();
    }


    public void UpdateShoppingNoteInLocalDB(String productname, String priority, String newCategory, int noteid) {
        ShoppingNoteEntity shoppingNoteEntity = new ShoppingNoteEntity(shoppingNote, priority, productname, noteid);
        shoppingNoteEntity.setProductCategory(newCategory);

        LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
        localDataBase.updateShoppingNote(shoppingNoteEntity);

        Toast.makeText(MyApplication.getAppContext(), " Update note done ", Toast.LENGTH_LONG).show();

    }

    public void UpdateOrdinaryNoteInLocalDB(String notecontent, String priority, int noteid) {

        OrdinaryNoteEntity ordinaryNoteEntity = new OrdinaryNoteEntity(ordinaryNote, priority, notecontent, noteid);
        LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
        localDataBase.updateOrdinaryNote(ordinaryNoteEntity);

        Toast.makeText(MyApplication.getAppContext(), " Update note done ", Toast.LENGTH_LONG).show();

    }

    public void UpdateMeetingNoteInLocalDB(String title, String place, String agenda, String meetingNoteDate,
                                           String Priority, String estimatedTransportTime, int noteid) {

        MeetingNoteEntity meetingNoteEntity = new MeetingNoteEntity(meetingNote, Priority, title, place, agenda, java.sql.Timestamp.valueOf(meetingNoteDate),
                Time.valueOf(estimatedTransportTime), noteid);

        LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
        localDataBase.updateMeetingNote(meetingNoteEntity);

        Toast.makeText(MyApplication.getAppContext(), " Update note done ", Toast.LENGTH_LONG).show();

    }

    public void UpdateDeadlineNoteInLocalDB(String title, String priority, String deadlinedate_time, int progressValue, int noteid) {
        DeadlineNoteEntity deadlineNoteEntity = new DeadlineNoteEntity(deadlineNote, priority, title, noteid);
        deadlineNoteEntity.setDeadLineDate(Timestamp.valueOf(deadlinedate_time));
        deadlineNoteEntity.setProgressPercentage(progressValue);
        LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
        localDataBase.UpdateDeadlineNote(deadlineNoteEntity);
        Toast.makeText(MyApplication.getAppContext(), " Update note done ", Toast.LENGTH_LONG).show();
    }

    public String GetNotSyncNotes() {

        long usetId = UserController.getCurrentUserID();
        LocalDataBase localDataBase = new LocalDataBase(MyApplication.getAppContext());
        JSONArray jsonArray = new JSONArray();
        NoteEntity note;
        Cursor res = localDataBase.SelectRecordsWithSyncZero();
        if (!res.moveToFirst())
            return "";
        String noteType, Priority;
        boolean isDone, isDeleted, isTextcat, isAdded, isUpdated;
        Timestamp creationDate;
        int localid;
        long serverNoteId;
        res.moveToFirst();
        do {
            noteType = res.getString(res.getColumnIndex("noteType")); // note type
            Log.i("noteType", noteType);

            if (noteType.equals(meetingNote)) {
                String meetingTitle;
                meetingTitle = res.getString(res.getColumnIndex("meetingTitle"));
                Priority = res.getString(res.getColumnIndex("Priority"));
                localid = res.getInt(res.getColumnIndex("localnoteId"));
                creationDate = Timestamp.valueOf(res.getString(res.getColumnIndex("creationDate")));
                isDone = IntToboolean(res.getInt(res.getColumnIndex("isDone")));
                isTextcat = IntToboolean(res.getInt(res.getColumnIndex("isTextCategorized")));
                java.sql.Timestamp MeetingDate = java.sql.Timestamp.valueOf(res.getString(res.getColumnIndex("meetingNoteDate")));
                Time EstimatedTime = Time.valueOf(res.getString(res.getColumnIndex("estimatedTransportTime")));
                String place = res.getString(res.getColumnIndex("meetingPlace"));
                String agenda = res.getString(res.getColumnIndex("meetingAgenda"));
                isAdded = IntToboolean(res.getInt(res.getColumnIndex("isAdded")));
                isDeleted = IntToboolean(res.getInt(res.getColumnIndex("isDeleted")));
                isUpdated = IntToboolean(res.getInt(res.getColumnIndex("isUpdated")));
                serverNoteId = Long.parseLong(res.getString(res.getColumnIndex("ServernoteId")));

                note = new MeetingNoteEntity(meetingNote, Priority, creationDate, isDone, isDeleted, isTextcat, isAdded
                        , meetingTitle, place, agenda, MeetingDate, EstimatedTime);
                note.setNoteId(localid);
                note.setUserId(usetId);
                note.setIsUpdated(isUpdated);
                note.setServernoteId(serverNoteId);

                JSONObject temp = new JSONObject();
                try {
                    temp.put("Meeting", note.toString());
                    jsonArray.put(temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //NoteRecords.add(note.toString());

            } else if (noteType.equals(deadlineNote)) {
                String deadLineTitle;
                int progressprecentage;
                Timestamp deadlineDate;
                deadLineTitle = res.getString(res.getColumnIndex("deadLineTitle"));
                Priority = res.getString(res.getColumnIndex("Priority"));
                localid = res.getInt(res.getColumnIndex("localnoteId"));
                creationDate = Timestamp.valueOf(res.getString(res.getColumnIndex("creationDate")));
                isDone = IntToboolean(res.getInt(res.getColumnIndex("isDone")));
                isTextcat = IntToboolean(res.getInt(res.getColumnIndex("isTextCategorized")));
                progressprecentage = res.getInt(res.getColumnIndex("progressPercentage"));
                deadlineDate = Timestamp.valueOf(res.getString(res.getColumnIndex("deadLineDate")));
                isAdded = IntToboolean(res.getInt(res.getColumnIndex("isAdded")));
                isDeleted = IntToboolean(res.getInt(res.getColumnIndex("isDeleted")));
                isUpdated = IntToboolean(res.getInt(res.getColumnIndex("isUpdated")));
                serverNoteId = Long.parseLong(res.getString(res.getColumnIndex("ServernoteId")));

                note = new DeadlineNoteEntity(deadlineNote, creationDate, isDone, isDeleted, isTextcat, isAdded, progressprecentage,
                        deadLineTitle, deadlineDate, Priority);
                note.setNoteId(localid);
                note.setUserId(usetId);
                note.setIsUpdated(isUpdated);
                note.setServernoteId(serverNoteId);

                JSONObject temp = new JSONObject();
                try {
                    temp.put("Deadline", note.toString());
                    jsonArray.put(temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (noteType.equals(shoppingNote)) {

                String productToBuy, Category;
                productToBuy = res.getString(res.getColumnIndex("productToBuy"));
                Priority = res.getString(res.getColumnIndex("Priority"));
                localid = res.getInt(res.getColumnIndex("localnoteId"));
                creationDate = Timestamp.valueOf(res.getString(res.getColumnIndex("creationDate")));
                isDone = IntToboolean(res.getInt(res.getColumnIndex("isDone")));
                isTextcat = IntToboolean(res.getInt(res.getColumnIndex("isTextCategorized")));
                Category = res.getString(res.getColumnIndex("productCategory"));
                isAdded = IntToboolean(res.getInt(res.getColumnIndex("isAdded")));
                isDeleted = IntToboolean(res.getInt(res.getColumnIndex("isDeleted")));
                isUpdated = IntToboolean(res.getInt(res.getColumnIndex("isUpdated")));
                serverNoteId = Long.parseLong(res.getString(res.getColumnIndex("ServernoteId")));

                note = new ShoppingNoteEntity(shoppingNote, Priority, creationDate, isDone, isDeleted, isTextcat, isAdded, productToBuy, Category);
                note.setNoteId(localid);
                note.setUserId(usetId);
                note.setIsUpdated(isUpdated);
                note.setServernoteId(serverNoteId);

                JSONObject temp = new JSONObject();
                try {
                    temp.put("Shopping", note);
                    jsonArray.put(temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (noteType.equals(ordinaryNote)) {
                String noteContent;
                noteContent = res.getString(res.getColumnIndex("noteContent"));
                Priority = res.getString(res.getColumnIndex("Priority"));
                localid = res.getInt(res.getColumnIndex("localnoteId"));
                creationDate = Timestamp.valueOf(res.getString(res.getColumnIndex("creationDate")));
                isDone = IntToboolean(res.getInt(res.getColumnIndex("isDone")));
                isTextcat = IntToboolean(res.getInt(res.getColumnIndex("isTextCategorized")));
                isAdded = IntToboolean(res.getInt(res.getColumnIndex("isAdded")));
                isDeleted = IntToboolean(res.getInt(res.getColumnIndex("isDeleted")));
                isUpdated = IntToboolean(res.getInt(res.getColumnIndex("isUpdated")));
                serverNoteId = Long.parseLong(res.getString(res.getColumnIndex("ServernoteId")));

                note = new OrdinaryNoteEntity(ordinaryNote, Priority, creationDate, isDone, isDeleted, isTextcat, noteContent, isAdded);
                note.setNoteId(localid);
                note.setUserId(usetId);
                note.setIsUpdated(isUpdated);
                note.setServernoteId(serverNoteId);
                JSONObject temp = new JSONObject();
                try {
                    temp.put("Ordinary", note.toString());
                    jsonArray.put(temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } while (res.moveToNext());

        res.close();
        return jsonArray.toString();
    }

    private boolean IntToboolean(int x) {
        return x != 0;
    }

    public void StartService() {
        // Toast.makeText(MyApplication.getAppContext(),"In note Controller ",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MyApplication.getAppContext(), SynchronizationService.class);
        intent.putExtra("Interval", 60);
        MyApplication.getAppContext().startService(intent);
    }
}
