package oldclasses;

public class OldSegment /*extends Entity*/ {

//	private final int HEAD_SCORE_VALUE = 100;
//	private final int BODY_SCORE_VALUE = 10;
//	private final int VELOCITY = 3;
//	private boolean isHead;
//	Random rand = new Random();
//
//	public boolean lastDirRight;
//	public int lastVerticalVelocity = VELOCITY;
//
//	public OldSegment(int x, int y, boolean isHead) {
//		this.setHead(isHead);
//		this.setLocation(x, y);
//		this.setSize(Game.GRID_SIZE, Game.GRID_SIZE);
//		setVelocity(VELOCITY, 0);
//		setHealth(3);
//		lastDirRight = true;
//	}
//
//	/**
//	 * @return the isHead
//	 */
//	public boolean isHead() {
//		return isHead;
//	}
//
//
//	@Override
//	public void act(ArrayList<Mushroom> grid) {
//		this.updateLocationUsingVelocity();
//
//		//Check if hit something.
//		boolean hitSomething = false;
//		for (Mushroom m : grid) {
//			if (checkOverlap(m))
//				hitSomething = true;
//		}
//		if (getXPos() + getRectangle().width >= Game.SCREEN_WIDTH ||
//				getXPos() <= 0 ||
//				getYPos() + getRectangle().height >= Game.SCREEN_HEIGHT ||
//				(getYPos() <= Game.SCREEN_HEIGHT - Game.PLAYER_ZONE_HEIGHT && getYVel() < 0)) { //Hit a screen border (or top of player zone moving up.)
//			hitSomething = true;
//		}
//
//		if (hitSomething && getYVel() == 0) {
//			//Needs to change to vertical motion
//			if (getYPos() + getRectangle().height >= Game.SCREEN_HEIGHT) //bottom
//			{ //hit bottom - move upwards now
//				lastVerticalVelocity = -VELOCITY;
//			}
//			else if ((getYPos() > Game.SCREEN_HEIGHT - Game.PLAYER_ZONE_HEIGHT) && getYVel() < 0) { //hit top of player zone moving upwards, move down again.
//				lastVerticalVelocity = VELOCITY;
//			}
//			setVelocity(0, lastVerticalVelocity); //move vertically.
//		}
//		else if (getYVel() != 0) //if currently moving up-down...
//		{
//			//Might return to horizontal motion.
//			if (getYPos() % Game.GRID_SIZE == 0) {
//				if (lastDirRight) {
//					lastDirRight = false;
//					setVelocity(-VELOCITY, 0); //going left
//				}
//				else {
//					lastDirRight = true;
//					setVelocity(VELOCITY, 0); //going right
//				}
//			}
//		}
//	} //--end of act() method--
//
//	@Override
//	public int onCollide(Entity e) {
//		if (e instanceof Arrow) {
//			setHealth(getHealth() - 1);
//
//			if(getHealth() <= 0) {
//				if (isHead) return HEAD_SCORE_VALUE;
//				else return BODY_SCORE_VALUE;
//			}
//		}
//		else if(e instanceof Explosion) {
//			setHealth(0);
//			if (isHead) return HEAD_SCORE_VALUE;
//			else return BODY_SCORE_VALUE;
//		}
//		return 0;
//	}
//
//	@Override
//	public void draw(Graphics g) {
//		// A segImg should draw itself, but it is fine to do this in Millipede.
//	}
//
//	/**
//	 * @param isHead the isHead to set
//	 */
//	public final void setHead(boolean isHead) {
//		this.isHead = isHead;
//	}
}