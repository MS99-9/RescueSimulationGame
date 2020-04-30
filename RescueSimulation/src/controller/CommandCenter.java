package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.PoliceUnit;
import model.units.Unit;
import model.units.UnitState;
import simulation.Rescuable;
import simulation.Simulator;
import view.ActiveDisasterPanel;
import view.Casualtiespanel;
import view.ENDGAME;
import view.Grid;
import view.Info;
import view.Log;
import view.Main;
import view.NumTurns;
import view.Respond;
import view.UnitsPanel;

public class CommandCenter implements SOSListener, ActionListener {

	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;


	private ArrayList<Unit> emergencyUnits;
	private Grid grid = new Grid();
	private Info info = new Info();
	private UnitsPanel unitsp = new UnitsPanel();
	private Main view;
	private JButton[][] gridbuttons = new JButton[10][10];
	private Log log = new Log();
	private ActiveDisasterPanel adp = new ActiveDisasterPanel();
	private ArrayList<JButton> availableUnitsbuttons = new ArrayList<JButton>();
	private ArrayList<Unit> availableUnits = new ArrayList<Unit>();
	private JButton NextTurn = new JButton("Next Turn");
	private NumTurns nt = new NumTurns();
	private Respond respond = new Respond();
	private JButton respondbutton = new JButton("RESCUE ME!");
	private int x, y;
	private Unit savedUnit;
	private Casualtiespanel csp = new Casualtiespanel();
	private int countdead = 0;
	private ArrayList<Citizen> dead = new ArrayList<Citizen>();
	private ArrayList<JButton> treatingunits = new ArrayList<JButton>();
	private ArrayList<JButton> respondingunits = new ArrayList<JButton>();

	public CommandCenter() throws Exception {

		NextTurn.addActionListener(this);
		nt.add(NextTurn);
		respondbutton.addActionListener(this);
		respond.add(respondbutton);
		engine = new Simulator(this);
		visibleBuildings = engine.getBuildings();
		visibleCitizens = engine.getCitizens();
		emergencyUnits = engine.getEmergencyUnits();
		log.setTextarea(DisasterInfo());
		adp.setTextarea(ActiveDisasterInfo());
		
		for (int i = 0; i < emergencyUnits.size(); i++) {
			Unit u = emergencyUnits.get(i);
			JButton b = new JButton();
			availableUnitsbuttons.add(b);
			b.addActionListener(this);
			availableUnits.add(emergencyUnits.get(i));
			if (u instanceof Evacuator) {
				JLabel e = new JLabel();
				b.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Mohamed Shalaby\\Desktop\\Evacuator2.png").getImage()
						.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
				unitsp.add(b);
			}
			if (u instanceof FireTruck) {

				b.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Mohamed Shalaby\\Desktop\\FireTruck2.png").getImage()
						.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
				unitsp.add(b);
			}
			if (u instanceof Ambulance) {
				b.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Mohamed Shalaby\\Desktop\\Ambulance2.png").getImage()
						.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

				unitsp.add(b);
			}
			if (u instanceof DiseaseControlUnit) {
				b.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Mohamed Shalaby\\Desktop\\DiseaseControlUnit.png")
						.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
				unitsp.add(b);
			}
			if (u instanceof GasControlUnit) {
				b.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Mohamed Shalaby\\Desktop\\GasControlUnit.png")
						.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
				unitsp.add(b);
			}

		}

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				gridbuttons[i][j] = new JButton();
				try {
					gridbuttons[i][j].addActionListener(this);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				grid.add(gridbuttons[i][j]);

			}
		}
		updateButtons();
		view = new Main(grid, info, unitsp, log, adp, nt, respond, csp);
		

	}

	public JButton[][] getGridbuttons() {
		return gridbuttons;
	}

	public Grid getGrid() {
		return grid;
	}

	@Override
	public void receiveSOSCall(Rescuable r) {

		if (r instanceof ResidentialBuilding) {

			if (!visibleBuildings.contains(r))
				visibleBuildings.add((ResidentialBuilding) r);

		} else {

			if (!visibleCitizens.contains(r))
				visibleCitizens.add((Citizen) r);
		}

	}

	public String CitizenInfo(Citizen e) {
		String f = "Location: " + e.getLocation().getX() + "," + e.getLocation().getY() + "Name: " + e.getName() + "\n"
				+ "Age: " + e.getAge() + "\n" + "National ID: " + e.getNationalID() + "\n" + "Hp: " + e.getHp() + "\n"
				+ "BloodLoss: " + e.getBloodLoss() + "\n" + "Toxicity: " + e.getToxicity() + "\n" + "State: "
				+ e.getState() + "\n";
		if (e.getDisaster() != null) {
			if (e.getDisaster().isActive())
				f += "Disaster: " + e.getDisaster().getname() + "\n";
		}
		return f;
	}

	public String BuildingInfo(ResidentialBuilding e) {
		String f = "Location: " + e.getLocation().getX() + "," + e.getLocation().getY() + "\n"
				+ "Structural Integrity: " + e.getStructuralIntegrity() + "\n" + "Fire Damage: " + e.getFireDamage()
				+ "\n" + "Gas Level: " + e.getGasLevel() + "\n" + "Foundation Damage: " + e.getFoundationDamage() + "\n"
				+ "Number of Occupants: " + e.getOccupants().size() + "\n" + "Citizens inside the building: " + "\n";
		for (int i = 0; i < e.getOccupants().size(); i++) {
			f += CitizenInfo(e.getOccupants().get(i)) + "\n";
		}
		if (e.getDisaster() != null) {
			if (e.getDisaster().isActive()) {
				f += "Disaster: " + e.getDisaster().getname() + "\n" + "Start Cycle: " + e.getDisaster().getStartCycle()
						+ "\n";
			}
		}
		return f;
	}

	public String UnitInfo(Unit e) {
		String f = "";
		f += "ID: " + e.getUnitID() + "\n" + "Type: " + e.getType() + "\n" + "Location: " + e.getLocation().getX() + ","
				+ e.getLocation().getY() + "\n" + "StepsPerCycle: " + e.getStepsPerCycle() + "\n" + "Target: ";
		if (e.getTarget() != null) {
			if (e instanceof Ambulance || e instanceof DiseaseControlUnit)
				f += CitizenInfo((Citizen) e.getTarget()) + "\n";
			else
				f += BuildingInfo((ResidentialBuilding) e.getTarget()) + "\n";
			f += "State: " + e.getState() + "\n";
		}
		if (e instanceof Evacuator) {
			f += "Number of passengers: " + ((PoliceUnit) e).getPassengers().size() + "\n";
			for (int i = 0; i < ((PoliceUnit) e).getPassengers().size(); i++) {
				f += CitizenInfo(((Evacuator) e).getPassengers().get(i)) + "\n";
			}
		}

		return f;
	}

	public String DisasterInfo() {
		String f = "";
		for (int i = 0; i < engine.getExecutedDisasters().size(); i++) {
			f += "Disaster:" + engine.getExecutedDisasters().get(i).getname() + "\n";
			if (engine.getExecutedDisasters().get(i).getTarget() instanceof Citizen) {
				f += "Target: " + ((Citizen) engine.getExecutedDisasters().get(i).getTarget()).getName();
			} else {
				f += "Target: ("
						+ ((ResidentialBuilding) engine.getExecutedDisasters().get(i).getTarget()).getLocation().getX()
						+ ","
						+ ((ResidentialBuilding) engine.getExecutedDisasters().get(i).getTarget()).getLocation().getY()
						+ ")";
			}
		}
		return f;
	}

	public String ActiveDisasterInfo() {
		String f = "";
		for (int i = 0; i < engine.getExecutedDisasters().size(); i++) {
			if (engine.getExecutedDisasters().get(i).isActive()) {
				f += "Disaster:" + engine.getExecutedDisasters().get(i).getname() + "\n";
				if (engine.getExecutedDisasters().get(i).getTarget() instanceof Citizen) {
					f += "Target: " + ((Citizen) engine.getExecutedDisasters().get(i).getTarget()).getName();
				} else {
					f += "Target: ("
							+ ((ResidentialBuilding) engine.getExecutedDisasters().get(i).getTarget()).getLocation()
									.getX()
							+ "," + ((ResidentialBuilding) engine.getExecutedDisasters().get(i).getTarget())
									.getLocation().getY()
							+ ")";
				}
			}
		}
		return f;
	}

	public String getinfo(int i, int j) {
		String info = "";
		for (int g = 0; g < visibleCitizens.size(); g++) {
			if (visibleCitizens.get(g).getLocation().getX() == i && visibleCitizens.get(g).getLocation().getY() == j) {
				Citizen e = visibleCitizens.get(g);
				info += CitizenInfo(e) + "\n";
			}
		}
		for (int g = 0; g < visibleBuildings.size(); g++) {
			if (visibleBuildings.get(g).getLocation().getX() == i
					&& visibleBuildings.get(g).getLocation().getY() == j) {
				ResidentialBuilding e = visibleBuildings.get(g);
				info += BuildingInfo(e) + "\n";
			}
		}
		for (int g = 0; g < emergencyUnits.size(); g++) {
			if (emergencyUnits.get(g).getLocation().getX() == i && emergencyUnits.get(g).getLocation().getY() == j) {
				Unit e = emergencyUnits.get(g);
				info += UnitInfo(e) + "\n";
			}
		}
		return info;
	}

	public void updateCycle() {
		if(engine.checkGameOver()) {
			view.setVisible(false);
			int countdead=0;
			for (int i = 0; i < visibleCitizens.size(); i++) {
				if (visibleCitizens.get(i).getState() == CitizenState.DECEASED) {
					countdead++;
					dead.add(visibleCitizens.get(i));
				}
				
			}
			ENDGAME end=new ENDGAME();
			end.updatexas(countdead);
		}
		engine.nextCycle();
		log.setTextarea(DisasterInfo());
		adp.setTextarea(ActiveDisasterInfo());
		updateCasualties();
		updateButtons();
		updateDeadlog();
		updateUnits();
	}

	public void updateUnits() {

		for (int j = 0; j < availableUnits.size(); j++) {
			if (availableUnits.get(j).getState() == UnitState.IDLE)
				availableUnitsbuttons.get(j).setVisible(true);
			else
				availableUnitsbuttons.get(j).setVisible(false);
		}

	}

	public void updateDeadlog() {
		String f = "";
		for (int i = 0; i < dead.size(); i++) {
			f += CitizenInfo((dead.get(i)));
		}
		log.getTextarea().append(f);
	}

	public void updateCasualties() {
		int count = countdead;
		for (int i = 0; i < visibleCitizens.size(); i++) {
			if (visibleCitizens.get(i).getState() == CitizenState.DECEASED) {
				countdead++;
				dead.add(visibleCitizens.get(i));
			}
		}
		csp.updateTurns(countdead - count);

	}

	public void updateButtons() {
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				gridbuttons[i][j].setIcon(new ImageIcon(
						new ImageIcon("Grid.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
			}
		}
		for (int i = 0; i < visibleCitizens.size(); i++) {
			if (visibleCitizens.get(i).getState() == CitizenState.DECEASED) {
				gridbuttons[visibleCitizens.get(i).getLocation().getX()][visibleCitizens.get(i).getLocation().getY()]
						.setIcon(new ImageIcon(
								new ImageIcon("Dead.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
			} else if (visibleCitizens.get(i).getState() == CitizenState.IN_TROUBLE) {
				gridbuttons[visibleCitizens.get(i).getLocation().getX()][visibleCitizens.get(i).getLocation().getY()]
						.setIcon(new ImageIcon(
								new ImageIcon("IT.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
			} else {
				gridbuttons[visibleCitizens.get(i).getLocation().getX()][visibleCitizens.get(i).getLocation().getY()]
						.setIcon(new ImageIcon(new ImageIcon("Citizen.jpg").getImage().getScaledInstance(100, 100,
								Image.SCALE_SMOOTH)));
			}

		}
		for (int i = 0; i < visibleBuildings.size(); i++) {
			if(visibleBuildings.get(i).getFireDamage()== 0 && visibleBuildings.get(i).getStructuralIntegrity() > 0 && visibleBuildings.get(i).getGasLevel() == 0 && visibleBuildings.get(i).getFoundationDamage() < 100) {
				
				
					if (visibleBuildings.get(i).getOccupants().size() == 0)
						gridbuttons[visibleBuildings.get(i).getLocation().getX()][visibleBuildings
								.get(i).getLocation().getY()]
										.setIcon(new ImageIcon(new ImageIcon("Building.png").getImage()
												.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
					else {
						gridbuttons[visibleBuildings.get(i).getLocation().getX()][visibleBuildings
								.get(i).getLocation().getY()]
										.setIcon(new ImageIcon(new ImageIcon("BuildingPeople.jpg").getImage()
												.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
					}
				}
			else
			if (visibleBuildings.get(i).getStructuralIntegrity() == 0) {
				gridbuttons[visibleBuildings.get(i).getLocation().getX()][visibleBuildings.get(i).getLocation().getY()]
						.setIcon(new ImageIcon(new ImageIcon("Collapsed.png").getImage().getScaledInstance(100, 100,
								Image.SCALE_SMOOTH)));
			} else {
				if (visibleBuildings.get(i).getDisaster() != null) {
						if (visibleBuildings.get(i).getOccupants().size() == 0)
							gridbuttons[visibleBuildings.get(i).getLocation().getX()][visibleBuildings.get(i)
									.getLocation().getY()]
											.setIcon(new ImageIcon(new ImageIcon("BuildingIntrouble.jpg").getImage()
													.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
						else {
							gridbuttons[visibleBuildings.get(i).getLocation().getX()][visibleBuildings.get(i)
									.getLocation().getY()]
											.setIcon(new ImageIcon(new ImageIcon("BuildingPeopleTrouble.png").getImage()
													.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
						}
					}
				
			else {
					if (visibleBuildings.get(i).getOccupants().size() == 0)
						gridbuttons[visibleBuildings.get(i).getLocation().getX()][visibleBuildings
								.get(i).getLocation().getY()]
										.setIcon(new ImageIcon(new ImageIcon("Building.png").getImage()
												.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
					else {
						gridbuttons[visibleBuildings.get(i).getLocation().getX()][visibleBuildings
								.get(i).getLocation().getY()]
										.setIcon(new ImageIcon(new ImageIcon("BuildingPeople.jpg").getImage()
												.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
					}
				}
			}
		}
	}

	public void respondbutton() {
		if (savedUnit != null && x != 0 && y != 0) {

			

			for (int i = 0; i < visibleBuildings.size(); i++) {
				if (visibleBuildings.get(i).getLocation().getX() == x
						&& visibleBuildings.get(i).getLocation().getY() == y) {
					try {
						savedUnit.respond(visibleBuildings.get(i));
						return;
					} catch (IncompatibleTargetException e) {
						JOptionPane.showMessageDialog(view, "Incompatible Target", "Can't Respond",
								JOptionPane.ERROR_MESSAGE);
						return;

					} catch (CannotTreatException e) {
						JOptionPane.showMessageDialog(view, "Cannot Treat Target", "Can't Respond",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}
			for (int i = 0; i < visibleCitizens.size(); i++) {
				if (visibleCitizens.get(i).getLocation().getX() == x
						&& visibleCitizens.get(i).getLocation().getY() == y) {
					try {
						savedUnit.respond(visibleCitizens.get(i));
						return;
					} catch (IncompatibleTargetException e) {
						JOptionPane.showMessageDialog(view, "Incompatible Target", "Can't Respond",
								JOptionPane.ERROR_MESSAGE);
						return;
					} catch (CannotTreatException e) {
						JOptionPane.showMessageDialog(view, "Cannot Treat Target", "Can't Respond",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}
		} else {
			JOptionPane.showMessageDialog(view, "No Unit Assigned", "Can't Respond", JOptionPane.ERROR_MESSAGE);

		}
	}

	public static void main(String[] args) {
		CommandCenter mygame = null;
		try {
			mygame = new CommandCenter();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++)
				if ((JButton) (e.getSource()) == gridbuttons[i][j]) {
					x = i;
					y = j;
					info.setTextarea(this.getinfo(i, j));
				}
		}
		for (int i = 0; i < availableUnitsbuttons.size(); i++) {
			if ((JButton) (e.getSource()) == availableUnitsbuttons.get(i)) {
				savedUnit = availableUnits.get(i);
				info.setTextarea(this.UnitInfo(availableUnits.get(i)));
			}
		}
		if ((JButton) (e.getSource()) == NextTurn) {
			updateCycle();
			nt.turns++;
			nt.updateTurns();
			System.out.println(
					availableUnits.get(0).getLocation().getX() + " " + availableUnits.get(0).getLocation().getY());
		}
		if ((JButton) (e.getSource()) == respondbutton) {
			respondbutton();
		}
	}
}
