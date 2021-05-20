package uk.ac.diamond.ispyb.api;

import java.util.Optional;

public interface IspybRobotApi {
    Optional<RobotAction> retrieveRobotAction(Long id);
    Long insertRobotAction(RobotAction robotAction);
}
