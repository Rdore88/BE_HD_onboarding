package portal.User;

import com.google.cloud.spanner.*;
import java.util.Collections;

public class userDatabase {


    private static DatabaseClient getDbClient() {
        SpannerOptions options = SpannerOptions.newBuilder().setProjectId("admin-portal-219814").build();
        Spanner spanner = options.getService();
        DatabaseId db = DatabaseId.of("admin-portal-219814", "admin-portal-instance", "admins" );
        DatabaseClient dbClient = spanner.getDatabaseClient(db);
        return dbClient;
    }

    public static ResultSet getAdminFromDatabase(String username) {
        DatabaseClient dbClient = getDbClient();
        Statement statement = Statement.newBuilder(
                "SELECT * FROM saved WHERE username = @username"
        )
                .bind("Username")
                .to(username)
                .build();
        ResultSet results = dbClient
                .singleUse()
                .executeQuery(statement);
        return results;
    }

    public static Boolean saveAdminToSpanner(User admin) {
        DatabaseClient dbClient = getDbClient();
        ResultSet results = getAdminFromDatabase(admin.getUsername());

        if (!results.next()){
            Mutation adminMutation = Mutation
                .newInsertBuilder("saved")
                .set("username")
                .to(admin.getUsername())
                .set("password")
                .to(admin.getPassword())
                .build();
            dbClient.write(Collections.singleton(adminMutation));
            return true;
        } else {
            return false;
        }
    }

    public static Boolean getAdminFromSpanner(User admin){
        ResultSet results = getAdminFromDatabase(admin.getUsername());

        if (results.next()){
            if (results.getString("password").equals(admin.getPassword()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}