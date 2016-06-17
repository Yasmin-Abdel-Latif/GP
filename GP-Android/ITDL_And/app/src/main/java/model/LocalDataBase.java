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

    static final String queryCreateTableNote = "CREATE TABLE " + tableName + "( " +
            collocalnoteId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            colServernoteId + " INTEGER ," +
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
            colisUpdated + " INTEGER," +
            colisDeleted + " INTEGER " + " );";

    static final String queryCreateTableOneUser = "CREATE TABLE " + tableName2 + "( " +
            coluniqueid + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            coluserid + " TEXT NOT NULL," +
            coltwittername + " TEXT" + " );";

    public LocalDataBase(Context context) {
        super(context, dataBaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(queryCreateTableNote);
        sqLiteDatabase.execSQL(queryCreateTableOneUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + queryCreateTableNote);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + queryCreateTableOneUser);
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

        long result = db.insert(tableName, null, values);
        return result;
    }

    public Cursor GetNotes() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        String qery = "SELECT * FROM " + tableName + " WHERE " + colisDeleted + " = " + 0;

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

    public void UpdateUserInfo(String userID, String userTwitter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(coluserid, userID);
        values.put(coltwittername, userTwitter);
        db.update(tableName2, values, coluniqueid + " = 1", null);
    }

    public String GetUserID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM  " + tableName2 + " where " + coluniqueid + " = 1", null);
        String userID = "";
        if (!cur.moveToFirst())
            return "";
        cur.moveToFirst();
        do {
            String UserID = cur.getString(cur.getColumnIndex(coluserid));
            String Twitter_Account = cur.getString(cur.getColumnIndex(coltwittername));
            JSONObject temp = new JSONObject();
            try {
                temp.put("UserID", UserID);
                temp.put("Twitter_Account", Twitter_Account);
                return temp.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } while (cur.moveToNext());

        cur.close();
        return "";
    }

    public long InsertUserInfo(String userID, String userTwitter) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(coluserid, userID);
        values.put(coltwittername, userTwitter);

        Log.i("userIdinDB=", userID);
        long result = db.insert(tableName2, null, values);
        return result;
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
        String qery = "SELECT * FROM " + tableName + " WHERE " + colisAdded + "= 0 or (" + colisAdded + "= 1 and " + colisDeleted + "= 1)  " +
                "or( " + colisAdded + "= 1 and " + colisUpdated + "= 1)";

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
}
