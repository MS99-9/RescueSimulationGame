package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Unit implements Simulatable, SOSResponder {
	private String unitID;
	private UnitState state;
	private Address location;
	private Rescuable target;
	private int distanceToTarget;
	private int stepsPerCycle;
	private WorldListener worldListener;

	public Unit(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {
		this.unitID = unitID;
		this.location = location;
		this.stepsPerCycle = stepsPerCycle;
		this.state = UnitState.IDLE;
		this.worldListener = worldListener;
	}

	public boolean canTreat(Rescuable r) {
		if (r instanceof Citizen) {
			Citizen e = (Citizen) r;
			if (e.getHp() >= 100) {
				return false;
			} 
			if(e.getDisaster() instanceof Injury && this instanceof GasControlUnit) {
				return false;
			}
			if(e.getDisaster() instanceof Infection && this instanceof FireTruck) {
				return false;
			}
				return true;
		} else {
			ResidentialBuilding e = (ResidentialBuilding) r;
			if (e.getFireDamage() == 0 && e.getGasLevel() == 0 && e.getFoundationDamage() == 0) {
				return false;
			}
			if (this instanceof GasControlUnit
					&& (e.getDisaster() instanceof Fire || e.getDisaster() instanceof Collapse)) {
				return false;
			}
			if (this instanceof Evacuator && (e.getDisaster() instanceof GasLeak || e.getDisaster() instanceof Fire)) {
				return false;
			}
			if (this instanceof FireTruck
					&& (e.getDisaster() instanceof GasLeak || e.getDisaster() instanceof Collapse)) {
				return false;
			}

			return true;
		}

	}

	public void setWorldListener(WorldListener listener) {
		this.worldListener = listener;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public UnitState getState() {
		return state;
	}

	public void setState(UnitState state) {
		this.state = state;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public String getUnitID() {
		return unitID;
	}

	public Rescuable getTarget() {
		return target;
	}

	public int getStepsPerCycle() {
		return stepsPerCycle;
	}

	public void setDistanceToTarget(int distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
	}

	@Override
	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException {

		if (target != null && state == UnitState.TREATING)
			reactivateDisaster();
		finishRespond(r);

	}

	public void reactivateDisaster() {

		Disaster curr = target.getDisaster();
		curr.setActive(true);

	}

	public void finishRespond(Rescuable r) throws IncompatibleTargetException, CannotTreatException {

		if (r instanceof Citizen) {

			if (this instanceof Ambulance || this instanceof DiseaseControlUnit) {
				if (!this.canTreat(r)) {
					throw new CannotTreatException(this, r);
				} else {
					target = r;
					state = UnitState.RESPONDING;
					Address t = r.getLocation();
					distanceToTarget = Math.abs(t.getX() - location.getX()) + Math.abs(t.getY() - location.getY());

				}
			} else {
				throw new IncompatibleTargetException(this, r);
			}
		} else {

			if (this instanceof Ambulance || this instanceof DiseaseControlUnit) {
				throw new IncompatibleTargetException(this, r);

			} else {
				if (!this.canTreat(r)) {
					throw new CannotTreatException(this, r);
				} else {
					target = r;
					state = UnitState.RESPONDING;
					Address t = r.getLocation();
					distanceToTarget = Math.abs(t.getX() - location.getX()) + Math.abs(t.getY() - location.getY());
				}
			}
		}

	}

	public abstract void treat();

	public void cycleStep() throws CannotTreatException {

		if (state == UnitState.IDLE)
			return;
		if (distanceToTarget > 0) {
			distanceToTarget = distanceToTarget - stepsPerCycle;
			if (distanceToTarget <= 0) {
				distanceToTarget = 0;
				Address t = target.getLocation();
				worldListener.assignAddress(this, t.getX(), t.getY());
			}
		} else {
			state = UnitState.TREATING;
			treat();
		}
	}

	public void jobsDone() {
		target = null;
		state = UnitState.IDLE;

	}
	public String getType() {
	 if(this instanceof Ambulance)return "Ambulance";
	 if(this instanceof DiseaseControlUnit)return "DiseaseControlUnit";
	 if(this instanceof Evacuator)return "Evacuator";
	 if(this instanceof FireTruck)return "FireTruck";
	 if(this instanceof GasControlUnit)return "GasControlUnit";
	 return "";
	}
}
