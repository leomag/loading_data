package service;

import com.aragost.javahg.BaseRepository;
import com.aragost.javahg.Changeset;
import com.aragost.javahg.Repository;
import com.aragost.javahg.commands.LogCommand;
import model.RepositoryData;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author i.isaev on 27.01.2020
 * @project unloading
 */
public class DatabaseManager extends DBConnection implements Runnable {

    private final static Logger LOG = Logger.getLogger(DatabaseManager.class);

    private static final String PATH_REPO = "/Users/isaev/Documents/uspd_spodes";

    private static final File FILE = new File(PATH_REPO);
/*    private static final Charset cs = Charset.forName("utf8");
    final CharsetDecoder charsetDecoder = cs.newDecoder();*/
    private static final BaseRepository REPOSITORY = Repository.open(FILE);
    //Repository.clone(new File(checkout_folder), url);
    private static final LogCommand LOG_COMMAND = LogCommand.on(REPOSITORY);
    private static final List<Changeset> CHANGESET_LIST = LOG_COMMAND.execute();

    private static volatile DatabaseManager instance;

    public static DatabaseManager getInstance() {
        DatabaseManager localInstance = instance;
        if (localInstance == null) {
            synchronized (DatabaseManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DatabaseManager();
                }
            }
        }
        return localInstance;
    }

    @Override
    public void run() {
        insertRepositoryData();
    }

    private void insertRepositoryData() {
        Connection dbConnection = null;

        ArrayList<RepositoryData> list =  write(CHANGESET_LIST);

        String sql = "INSERT INTO repository_data (author, commit_date, branch, changeset_number, message) VALUES " + "(?, ?, ?, ?, ?)";

        try {
            dbConnection = connection();
            PreparedStatement st = dbConnection.prepareStatement(sql);
            try {
                for (RepositoryData repoDate : list) {
                    st.setString(1, repoDate.getAuthor());
                    st.setTimestamp(2, repoDate.getCommitDate());
                    st.setString(3, repoDate.getBranch());
                    st.setString(4, repoDate.getChangesetNumber());
                    st.setString(5, repoDate.getMessage());
                    LOG.info("repository_data: " + repoDate.toString());
                    st.execute();
                }
            } catch (Exception e) {
            LOG.error("Duplicate");
            dbConnection.close();
        }
            dbConnection.close();
        } catch (IOException | SQLException e) {
            LOG.error(e.getMessage());
        }
    }

    private ArrayList<RepositoryData> write(List<Changeset> changesetList) {

        ArrayList<RepositoryData> repositoryDataArrayList = new ArrayList<>();

        for (Changeset changeset : changesetList) {
            //
            RepositoryData repositoryData = new RepositoryData();
            repositoryData.setAuthor(changeset.getUser());
            java.util.Date date = changeset.getTimestamp().getDate();
            repositoryData.setCommitDate(new Timestamp(date.getTime()));
            repositoryData.setBranch(changeset.getBranch());
            repositoryData.setChangesetNumber(String.valueOf(changeset.getRevision()));
            repositoryData.setMessage(changeset.getMessage());
            repositoryDataArrayList.add(repositoryData);

        }
        return repositoryDataArrayList;
    }

}
