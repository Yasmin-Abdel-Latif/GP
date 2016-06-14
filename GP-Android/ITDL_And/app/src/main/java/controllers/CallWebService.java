package controllers;

import android.os.AsyncTask;

import java.sql.Timestamp;

/**
 * Created by samah on 29/04/2016.
 */
public class CallWebService extends AsyncTask<String, Void, String> {


    static final public  String ordinaryNote = "Ordinary";
    static final public String meetingNote = "Meeting";
    static final public String deadlineNote = "Deadline";
    static final public String shoppingNote = "Shopping";

    @Override
    protected String doInBackground(String... params) {
        String serviceType;
        serviceType =params[params.length - 1];
        String urlParameters="";


        if (serviceType.equals("LoginService"))
            urlParameters = "useremail=" + params[1] + "&userpassword=" + params[2];

        else if(serviceType.equals("RegistrationService"))
            urlParameters = "username=" + params[1] + "&useremail=" + params[2]
                    + "&userpassword=" + params[3]+"&gender="+params[4] +"&city="+params[5]
                    +"&birth_date="+params[6]+"&Twitter_Account="+params[7];

        else if (serviceType.equals("GetUserInfoService")){
            urlParameters="userId="+params[1];

        }
        else if(serviceType.equals("UpdateProfileService")){
            urlParameters="userId="+params[1]+"&username=" + params[2] + "&useremail=" + params[3]
                    + "&userpassword=" + params[4]+"&gender="+params[5] +"&city="+params[6]
                    +"&birth_date="+params[7]+"&Twitter_Account="+params[8];


        }
      else  if(serviceType.equals("addOrdinaryNoteService"))
        {
            java.util.Date date = new java.util.Date();
            boolean isDone = false;
            boolean isTextCategorized = false;

            urlParameters = "ordinaryNoteContent=" + params[1] + "&creationDate="
                    + String.valueOf(new Timestamp(date.getTime())) + "&isDone=" + isDone + "&isTextCategorized="
                    + isTextCategorized + "&noteType=" + ordinaryNote + "&userID=" + params[2];
        }

        else  if(serviceType.equals("addShoppingNoteService")){
            java.util.Date date = new java.util.Date();
            boolean isDone = false;
            boolean isTextCategorized = false;

            urlParameters = "productToBuy=" + params[1] + "&productCategory="+params[2]+"&creationDate="
                    + String.valueOf(new Timestamp(date.getTime())) + "&isDone=" + isDone + "&isTextCategorized="
                    + isTextCategorized + "&noteType=" + shoppingNote + "&userID=" + params[3];


        }

        else if(serviceType.equals("addDeadLineNoteService")){
            java.util.Date date = new java.util.Date();
            boolean isDone = false;
            boolean isTextCategorized = false;
            urlParameters = "deadLineTitle=" + params[1] + "&deadLineDate="+params[2]+"&progressPercentage="+params[3]+"&creationDate="
                    + String.valueOf(new Timestamp(date.getTime())) + "&isDone=" + isDone + "&isTextCategorized="
                    + isTextCategorized + "&noteType=" + deadlineNote + "&userID=" + params[4];


        }
        //title,place,agenda,date,time
        else if(serviceType.equals("addMeetingNoteService")){
            java.util.Date date = new java.util.Date();
            boolean isDone = false;
            boolean isTextCategorized = false;
            urlParameters = "meetingTitle=" + params[1] + "&meetingPlace="+params[2]+"&meetingAgenda="+params[3]+"&meetingNoteDate="
                    +params[4]+"&estimatedTransportTime=" +params[5]
                    + "&creationDate="+String.valueOf(new Timestamp(date.getTime())) + "&isDone=" + isDone + "&isTextCategorized="
                    + isTextCategorized + "&noteType=" + meetingNote + "&userID=" + params[6]+"&priority="+params[7];


        }
        else if (serviceType.equals("synchroinzationService")){
            urlParameters = "NoteList=" + params[1];
        }

        else if (serviceType.equals("AddUserPostService")){
            urlParameters = "userID=" + params[1] + "&postID=" + params[2] + "&postContent=" + params[3] + "&creationDate=" + params[4];
        }

        else if (serviceType.equals("GetPostsService")){
            urlParameters = "userID=" + params[1];
        }

        String n =new Connection().connect(params[0],urlParameters);

        return n;
    }
}
