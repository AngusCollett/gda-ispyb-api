package uk.ac.diamond.ispyb.dao;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import uk.ac.diamond.ispyb.api.IspybSpyCatApi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class IspybSpyCatDAO  implements IspybSpyCatApi {

    private final TemplateWrapper templateWrapper;

    public IspybSpyCatDAO(TemplateWrapper templateWrapper, BeanTemplateWrapper beanTemplateWrapper){
        this.templateWrapper = templateWrapper;
    }

    @Override
    public List<String> getVisitsForUser(String beamline, String username, int tolerance) throws SQLException {
        Map<String, Object> _map = new HashMap<>();
        _map.put("beamline", beamline);
        _map.put("fed_id", username);
        _map.put("tolerance_minutes", tolerance);
        return templateWrapper.callIspybForList("retrieve_current_sessions_for_person", String.class, _map);
    }

    @Override
    public Optional<String> getLatestVisitWithPrefix(String beamline, String prefix) throws SQLException {
        Map<String, Object> _map = new HashMap<>();
        _map.put("beamline", beamline);
        _map.put("proposal_code", prefix);
        return templateWrapper.callIspyb("retrieve_most_recent_session", String.class, _map);
    }

    @Override
    public Optional<String> getTitleForVisit(String proposalCode, Integer proposalNumber) {
        Map<String, Object> _map = new HashMap<>();
        _map.put("proposal_code", proposalCode);
        _map.put("proposal_number", proposalNumber);
        return templateWrapper.callIspybFunction("retrieve_proposal_title", String.class, _map);
    }

    @Override
    public void close() throws IOException {
        try {
            templateWrapper.closeConnection();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
