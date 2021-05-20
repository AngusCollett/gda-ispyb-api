package uk.ac.diamond.ispyb.api;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IspybSpyCatApi extends Closeable {
    public List<String> getVisitsForUser(String beamline, String username, int tolerance) throws SQLException;
    public Optional<String> getLatestVisitWithPrefix(String beamline, String prefix) throws SQLException;
    public Optional<String> getTitleForVisit(String proposalCode, Integer proposalNumber);
}