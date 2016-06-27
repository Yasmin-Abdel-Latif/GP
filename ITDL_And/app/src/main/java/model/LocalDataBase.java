package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by samah on 21/03/2016.
 */
public class LocalDataBase extends SQLiteOpenHelper {

    static final String dataBaseName = "/mnt/sdcard/ITDL/MyNotes.db";
    //static final String dataBaseName="MYNotes.db";
    static final String coluniqueid = "Id";
    static final String tableName = "note";
    static final String tableName2 = "UserInfo";
    static final String collocalnoteId = "localnoteId";
    static final String colServernoteId = "ServernoteId";
    static final String colnoteContent = "noteContent";
    static final String colcreationDate = "creationDate";
    static final String colisDone = "isDone";
    static final String colisTextCategorized = "isTextCategorized";
    static final String colnoteType = "noteType";
    static final String colPriority = "Priority";
    static final String colproductCategory = "productCategory";
    static final String colproductToBuy = "productToBuy";
    static final String colmeetingNoteDate = "meetingNoteDate";
    static final String colestimatedTransportTime = "estimatedTransportTime";
    static final String colmeetingTitle = "meetingTitle";
    static final String colmeetingPlace = "meetingPlace";
    static final String colmeetingAgenda = "meetingAgenda";
    static final String colprogressPercentage = "progressPercentage";
    static final String coldeadLineTitle = "deadLineTitle";
    static final String coldeadLineDate = "deadLineDate";
    static final String colisUpdated = "isUpdated";
    static final String colisAdded = "isAdded";
    static final String colisDeleted = "isDeleted";
    static final String coltwittername = "twitterUserName";
    static final String coluserid = "userID";
    static final String colisDoneOnServer = "colisDoneOnServer";
    static final String tableAlarms = "Alarms";
    static final String requestcode = "Requestcode";
    static final String tableOneCellAlarms = "OneCellAlarms";
    static final String colOneCellAlarms = "OneCell";
    static final String colIsLoggedIn="IsLoggedIn";
    static final String coluserEmail = "userEmail";
    static final String coluserPass = "userPassword";

    static final String queryCreateTableNote = "CREATE TABLE " + tableName + "( " +
            collocalnoteId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            colServernoteId + " INTEGER ," +
            coluserid + " INTEGER ," +
            colnoteContent + " TEXT ," +
            colcreationDate + " date NOT NULL," +
            colnoteType + " TEXT NOT NULL ," +
            colproductCategory + " TEXT," +
            colproductToBuy + " TEXT," +
            colmeetingNoteDate + " date," +
            colestimatedTransportTime + " TEXT ," +
            colmeetingTitle + " TEXT ," +
            colmeetingPlace + " TEXT ," +
            colmeetingAgenda + " TEXT ," +
            colprogressPercentage + " INTEGER ," +
            coldeadLineTitle + " TEXT ," +
            coldeadLineDate + " date," +
            colisDone + " INTEGER," +
            colisTextCategorized + " INTEGER," +
            colPriority + " TEXT," +
            colisAdded + " INTEGER," +
            colisDoneOnServer + " INTEGER," +
            colisUpdated + " INTEGER," +
            colisDeleted + " INTEGER " + " );";

    static final String queryCreateTableOneUser = "CREATE TABLE " + tableName2 + "( " +
            coluniqueid + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            coluserid + " TEXT NOT NULL," +
            colIsLoggedIn +" INTEGER , "+
            coluserEmail +" TEXT NOT NULL , "+
            coluserPass +" TEXT , "+
            coltwittername + " TEXT" + " );";

    static final String queryCreateTableMeetingAlarms =
            "CREATE TABLE " + tableAlarms + "( " +
                    coluniqueid + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    collocalnoteId + " INTEGER, " +
                    requestcode + " INTEGER  );";


    static final String queryCreateTableOneCellAlarms =
            "CREATE TABLE " + tableOneCellAlarms + "( " +
                    colOneCellAlarms + " INTEGER  );";

    public LocalDataBase(Context context) {
        super(context, dataBaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(queryCreateTableNote);
        sqLiteDatabase.execSQL(queryCreateTableOneUser);
        sqLiteDatabase.execSQL(queryCreateTableMeetingAlarms);
        sqLiteDatabase.execSQL(queryCreateTableOneCellAlarms);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + queryCreateTableNote);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + queryCreateTableOneUser);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + queryCreateTableMeetingAlarms);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + queryCreateTableOneCellAlarms);
        onCreate(sqLiteDatabase);
    }


    public long InsertOrdinaryNote(OrdinaryNoteEntity note) {
        SQLiteDatabase db = this.getWritableDatabase();
        int isdone = note.isDone() ? 1 : 0;
        int isadded = note.isAdded() ? 1 : 0;
        int isTextcat = note.isTextCategorized() ? 1 : 0;
        int isupdated = 0;
        int isdeleted = 0;

        ContentValues values = new ContentValues();
        values.put(colnoteContent, note.getNoteContent());
        values.put(colcreationDate, String.valueOf(note.getNoteDateCreation()));
        values.put(colnoteType, note.getNoteType());
        values.put(colisDone, isdone);
        values.put(colisTextCategorized, isTextcat);
        values.put(colPriority, note.getNotePriority());
        values.put(colisDeleted, isdeleted);
        values.put(colisAdded, isadded);
        values.put(colisUpdated, isupdated);
        values.put(colServernoteId, note.getServernoteId());
        values.put(coluserid, note.getUserId());
        values.put(colisDoneOnServer, 0);

        Log.i("ServerNameidinDB=", String.valueOf(note.getServernoteId()));
        long result = db.insert(tableName, null, values);
        return result;
    }

    public long InsertShoppingNote(ShoppingNoteEntity note) {
        SQLiteDatabase db = this.getWritableDatabase();
        int isdone = note.isDone() ? 1 : 0;
        int isadded = note.isAdded() ? 1 : 0;
        int isTextcat = note.isTextCategorized() ? 1 : 0;
        int isupdated = 0;
        int isdeleted = 0;

        ContentValues values = new ContentValues();
        values.put(colnoteType, note.getNoteType());
        values.put(colisDone, isdone);
        values.put(colisTextCategorized, isTextcat);
        values.put(colPriority, note.getNotePriority());
        values.put(colcreationDate, String.valueOf(note.getNoteDateCreation()));
        values.put(colproductCategory, note.getProductCategory());
        values.put(colproductToBuy, note.getProductToBuy());
        values.put(colisAdded, isadded);
        values.put(colisUpdated, isupdated);
        values.put(colisDeleted, isdeleted);
        values.put(colServernoteId, note.getServernoteId());
        values.put(coluserid, note.getUserId());
        values.put(colisDoneOnServer, 0);


        long result = db.insert(tableName, null, values);
        return result;
    }

    public long InsertMeetingNote(MeetingNoteEntity meetingNoteEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        int isdone = meetingNoteEntity.isDone() ? 1 : 0;
        int isTextcat = meetingNoteEntity.isTextCategorized() ? 1 : 0;
        int isadded = meetingNoteEntity.isAdded() ? 1 : 0;
        int isupdated = 0;
        int isdeleted = 0;

        ContentValues values = new ContentValues();
        values.put(colnoteType, meetingNoteEntity.getNoteType());
        values.put(colisDone, isdone);
        values.put(colisTextCategorized, isTextcat);
        values.put(colcreationDate, String.valueOf(meetingNoteEntity.getNoteDateCreation()));
        values.put(colmeetingAgenda, meetingNoteEntity.getMeetingAgenda());
        values.put(colPriority, meetingNoteEntity.getNotePriority());
        values.put(colmeetingPlace, meetingNoteEntity.getMeetingPlace());
        values.put(colmeetingTitle, meetingNoteEntity.getMeetingTitle());
        values.put(colmeetingNoteDate, String.valueOf(meetingNoteEntity.getMeetingNoteDate()));
        values.put(colestimatedTransportTime, String.valueOf(meetingNoteEntity.getEstimatedTransportTime()));
        values.put(colisAdded, isadded);
        values.put(colisUpdated, isupdated);
        values.put(colisDeleted, isdeleted);
        values.put(colServernoteId, meetingNoteEntity.getServernoteId());
        values.put(coluserid, meetingNoteEntity.getUserId());
        values.put(colisDoneOnServer, 0);

        long result = db.insert(tableName, null, values);
        return result;
    }

    public long InsertDeadlineNote(DeadlineNoteEntity deadlineNoteEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        int isdone = deadlineNoteEntity.isDone() ? 1 : 0;
        int isTextcat = deadlineNoteEntity.isTextCategorized() ? 1 : 0;
        int isadded = deadlineNoteEntity.isAdded() ? 1 : 0;
        int isupdated = 0;
        int isdeleted = 0;

        ContentValues values = new ContentValues();
        values.put(colnoteType, deadlineNoteEntity.getNoteType());
        values.put(colisDone, isdone);
        values.put(colisTextCategorized, isTextcat);
        values.put(colcreationDate, String.valueOf(deadlineNoteEntity.getNoteDateCreation()));
        values.put(coldeadLineTitle, deadlineNoteEntity.getDeadLineTitle());
        values.put(coldeadLineDate, String.valueOf(deadlineNoteEntity.getDeadLineDate()));
        values.put(colprogressPercentage, String.valueOf(deadlineNoteEntity.getProgressPercentage()));
        values.put(colPriority, deadlineNoteEntity.getNotePriority());
        values.put(colisAdded, isadded);
        values.put(colisUpdated, isupdated);
        values.put(colisDeleted, isdeleted);
        values.put(colServernoteId, deadlineNoteEntity.getServernoteId());
        values.put(coluserid, deadlineNoteEntity.getUserId());
        values.put(colisDoneOnServer, 0);

        long result = db.insert(tableName, null, values);
        return result;
    }

    public long InsertUserInfo(String userID, String userTwitter, String userEmail, String userPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(coluserid, userID);
        values.put(coltwittername, userTwitter);
        values.put(coluserEmail, userEmail);
        values.put(coluserPass, userPassword);
        values.put(colIsLoggedIn, 1);

        Log.i("userIdinDB=", userID);
        long result = db.insert(tableName2, null, values);
        return result;
    }

    public long InsertNoteAlarm(int noteID, int alarmID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(collocalnoteId, noteID);
        values.put(requestcode, alarmID);

        long result = db.insert(tableAlarms, null, values);
        return result;
    }

    public Cursor SelectHistoryNotes(long userid) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        String qery = "SELECT * FROM " + tableName + " WHERE " + colisDeleted + " = " + 0 + " and "
                + coluserid + " = " + userid + " and " + colisDone + " = " + 1;

        c = db.rawQuery(qery, null);
        return c;
    }

    //Current Notes
    public Cursor SelectCurrentNotes(long userid) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        String qery = "SELECT * FROM " + tableName + " WHERE " + colisDeleted + " = " + 0 + " and "
                + coluserid + " = " + userid + " and " + colisDone + " = " + 0;
        c = db.rawQuery(qery, null);
        return c;
    }

    public Cursor GetNoteById(int noteId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        String qery = "SELECT * FROM " + tableName + " WHERE " + collocalnoteId + "=" + noteId;
        c = db.rawQuery(qery, null);
        return c;
    }

    public Cursor GetAlarmByNoteId(int noteId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        String qery = "SELECT * FROM " + tableAlarms + " WHERE " + collocalnoteId + "=" + noteId;
        c = db.rawQuery(qery, null);
        return c;
    }

    public void updateShoppingNote(ShoppingNoteEntity shoppingNoteEntity) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colproductCategory, shoppingNoteEntity.getProductCategory());
        cv.put(colproductToBuy, shoppingNoteEntity.getProductToBuy());
        cv.put(colPriority, shoppingNoteEntity.getNotePriority());
        cv.put(colisUpdated, 1);
        db.update(tableName, cv, collocalnoteId + " = " + shoppingNoteEntity.getNoteId(), null);
    }


    public void updateOrdinaryNote(OrdinaryNoteEntity ordinaryNoteEntity) {

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colnoteContent, ordinaryNoteEntity.getNoteContent());
        cv.put(colPriority, ordinaryNoteEntity.getNotePriority());
        cv.put(colisUpdated, 1);

        db.update(tableName, cv, collocalnoteId + " = " + ordinaryNoteEntity.getNoteId(), null);
    }

    public void updateMeetingNote(MeetingNoteEntity meetingNoteEntity) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(colmeetingAgenda, meetingNoteEntity.getMeetingAgenda());
        values.put(colPriority, meetingNoteEntity.getNotePriority());
        values.put(colmeetingPlace, meetingNoteEntity.getMeetingPlace());
        values.put(colmeetingTitle, meetingNoteEntity.getMeetingTitle());
        values.put(colmeetingNoteDate, String.valueOf(meetingNoteEntity.getMeetingNoteDate()));
        values.put(colestimatedTransportTime, String.valueOf(meetingNoteEntity.getEstimatedTransportTime()));
        values.put(colisUpdated, 1);

        db.update(tableName, values, collocalnoteId + " = " + meetingNoteEntity.getNoteId(), null);
    }

    public void UpdateDeadlineNote(DeadlineNoteEntity deadlineNoteEntity) {

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(colPriority, deadlineNoteEntity.getNotePriority());
        values.put(coldeadLineTitle, deadlineNoteEntity.getDeadLineTitle());
        values.put(colprogressPercentage, String.valueOf(deadlineNoteEntity.getProgressPercentage()));
        values.put(coldeadLineDate, String.valueOf(deadlineNoteEntity.getDeadLineDate()));
        values.put(colisUpdated, 1);
        db.update(tableName, values, collocalnoteId + " = " + deadlineNoteEntity.getNoteId(), null);
    }

    public void UpdateUserInfo(String userID, String userTwitter, String userEmail, String userPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(coluserid, userID);
        values.put(coltwittername, userTwitter);
        values.put(coluserEmail, userEmail);
        values.put(coluserPass, userPassword);
        values.put(colIsLoggedIn, 1);
        db.update(tableName2, values, coluniqueid + " = 1", null);
    }

    public int UpdateAlarmCell() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int alarmID = 0;
        if (GetAlarmID().equals("")) {
            alarmID = 1;
            values.put(colOneCellAlarms, alarmID);
            db.insert(tableOneCellAlarms, null, values);
        } else {
            alarmID = Integer.parseInt(GetAlarmID());
            alarmID++;
            String query = "UPDATE " + tableOneCellAlarms + " SET " + colOneCellAlarms + " = " + alarmID;
            db.execSQL(query);
        }

        return alarmID;
    }

    public String GetAlarmID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM  " + tableOneCellAlarms, null);
        if (!cur.moveToFirst())
            return "";
        cur.moveToFirst();
        String alarmID = cur.getString(cur.getColumnIndex(colOneCellAlarms));
        return alarmID;
    }

    public String GetUserID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM  " + tableName2 + " where " + coluniqueid + " = 1", null);
        if (!cur.moveToFirst())
            return "";
        cur.moveToFirst();
        do {
            String UserID = cur.getString(cur.getColumnIndex(coluserid));
            String Twitter_Account = cur.getString(cur.getColumnIndex(coltwittername));
            int isLoggedin = cur.getInt(cur.getColumnIndex(colIsLoggedIn));
            String userEmail = cur.getString(cur.getColumnIndex(coluserEmail));
            String userPassword = cur.getString(cur.getColumnIndex(coluserPass));
            JSONObject temp = new JSONObject();
            try {
                temp.put("UserID", UserID);
                temp.put("Twitter_Account", Twitter_Account);
                temp.put("IsLoggedIn", isLoggedin);
                temp.put("UserEmail", userEmail);
                temp.put("UserPassword", userPassword);
                return temp.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } while (cur.moveToNext());

        cur.close();
        return "";
    }

    public void LogoutUserID() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + tableName2 + " SET " + colIsLoggedIn + " = 0";
        db.execSQL(query);
    }

    public void ResetUpdate(int noteid) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colisUpdated, 0);
        db.update(tableName, cv, collocalnoteId + " = " + noteid, null);
    }


    public Cursor SelectRecordsWithSyncZero() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        String qery = "SELECT * FROM " + tableName + " WHERE " + colisAdded + "= 0 or (" + colisAdded + "= 1 and " + colisDeleted + "= 1 ) " +
                "or( " + colisAdded + "= 1 and " + colisUpdated + "= 1) or( " + colisAdded + "= 1 and " + colisDone + "= 1 and " + colisDoneOnServer + "= 0 )";

        c = db.rawQuery(qery, null);
        return c;
    }

    public void SycAdd(long servernoteid, int noteid) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(colisAdded, 1);
        values.put(colServernoteId, servernoteid);
        db.update(tableName, values, collocalnoteId + " = " + noteid, null);
    }

    public void DeleteNote(int noteid) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colisDeleted, 1);
        db.update(tableName, cv, collocalnoteId + " = " + noteid, null);

    }

    public void DeleteNotePermanently(int noteid) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + tableName + " WHERE " + collocalnoteId + "='" + noteid + "'");
        db.close();

    }

    public void DeleteNoteAlarmPermanently(int alarmid) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + tableAlarms + " WHERE " + requestcode + "='" + alarmid + "'");
        db.close();
    }

    public void DeleteNoteAlarmPermanentlyByNoteID(int noteID) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + tableAlarms + " WHERE " + collocalnoteId + "='" + noteID + "'");
        db.close();
    }

    public void SycDone(int noteid) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(colisDoneOnServer, 1);
        db.update(tableName, values, collocalnoteId + " = " + noteid, null);
    }

    public void SetIsDone(int noteid) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colisDone, 1);
        db.update(tableName, cv, collocalnoteId + " = " + noteid, null);

    }
}
