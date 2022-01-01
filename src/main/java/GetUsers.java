import Database_HY359.src.database.tables.EditSimpleUserTable;
import Database_HY359.src.mainClasses.SimpleUser;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "GetUsers", value = "/GetUsers")
public class GetUsers extends HttpServlet {
    private void createResponse(HttpServletResponse response, int statuscode, String data) {
        response.setStatus(statuscode);

        PrintWriter respwr = null;
        try {
            respwr = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        respwr.write(data);
        response.setContentType("application/text");
        response.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EditSimpleUserTable usertable = new EditSimpleUserTable();
        ArrayList<SimpleUser> users=null;
        JSONObject jsonreply = new JSONObject();

        try {
            users = usertable.databaseToSimpleUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for(SimpleUser user : users){
            jsonreply.append("users",usertable.simpleUserToJSON(user));
        }

        createResponse(response,200,jsonreply.toString());
    }
}
