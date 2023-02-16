package entitats;

import interficies.TesteableEntity;
import java.sql.Date;

/**
 *
 * @author pablomorante
 */
public class Missio implements TesteableEntity {
	private int cosmicMissionCode;
	private String targetName;
	private float missionBudget;
	private Date limitDate;
	private boolean accomplished;
}

