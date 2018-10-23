package portal.User;

import com.google.api.client.util.Lists;
import com.google.api.gax.paging.Page;
import com.google.api.services.storage.model.Bucket;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.spanner.*;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import javax.swing.plaf.nimbus.State;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class userDatabase {

    public userDatabase(){
//        SpannerOptions options = SpannerOptions.newBuilder().build();
//        Spanner spanner = options.getService();
//        DatabaseId db = DatabaseId.of(options.getProjectId(), "admin-portal-instance", "admins" );
//        DatabaseClient dbClient = spanner.getDatabaseClient(db);
//        this.dbClient = dbClient;
    }


    private static DatabaseClient getDbClient() {
        SpannerOptions options = SpannerOptions.newBuilder().setProjectId("admin-portal-219814").build();
        Spanner spanner = options.getService();
        DatabaseId db = DatabaseId.of("admin-portal-219814", "admin-portal-instance", "admins" );
        System.out.println(options.getProjectId());
        DatabaseClient dbClient = spanner.getDatabaseClient(db);
        System.out.println(dbClient);
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