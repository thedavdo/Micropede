package oldclasses;

/**
 * The Millipede class REVISION 2019: Added some safety logic to onCollide
 * method and fixed image code Millipede() now constructs at random row
 */

public class OldMillipede /*extends Entity*/ {

//	private Game game;
//
//	public ArrayList<OldSegment> segments = new ArrayList<OldSegment>();
//	Random rand = new Random();
//
//
//	public OldMillipede(Game game, int numSegments) // numSegments must be positive.
//	{
//		this.game = game;
//		// Choose a location to spawn at (use row = -1, column = varies by segImg)
//		addSegment((numSegments) * Game.GRID_SIZE, 0, true); // headImg
//		for (int z = 1; z < numSegments; z++) // add the body pieces
//		{
//			addSegment((numSegments - z) * Game.GRID_SIZE, 0, false);
//		}
//	}
//
//	/**
//	 * Adds a segImg to the millipede
//	 *
//	 * @param x      the x pos to create it at
//	 * @param y      the y pos to create it at
//	 * @param isHead
//	 */
//	private void addSegment(int x, int y, boolean isHead) {
//
//		OldSegment seg = new OldSegment(x, y, isHead);
//		segments.add(seg);
//		game.opp.add(seg);
//	}
//
//
//	/**
//	 * Passes on the act function onto the segments of the millipede
//	 */
//	@Override
//	public void act(ArrayList<Mushroom> grid) {
//		// Remove dead segments
//		for (int i = segments.size() - 1; i >= 0; i--) {
//			if (segments.get(i).isDead()) {
//				segments.remove(i);
//			}
//		}
//	}
//
//	@Override
//	public boolean checkOverlap(Entity e) {
//		boolean result = false;
//		for (OldSegment s : segments) {  //If any Segment is hit, the Millipede is hit.
//			result = result || s.checkOverlap(e);
//		}
//		return result;
//	}
//
//	@Override
//	public int onCollide(Entity e) {
//		int points = 0;
//
//		return points;
//	}
//
//	//	public void removeSegment(long id) {
////		for (int i = 0; i < segments.size(); i++) {
////			if (segments.get(i).getID() == id) {
////				segments.remove(i);
////			}
////		}
////	}
//	int segFrame = 0;
//	double loop = 1;
//
//	/**
//	 * Draws all the segments of the Millipede on screen
//	 */
//	@Override
//	public void draw(Graphics g) {
//		// Temporary
//		g.setColor(Color.MAGENTA);
//		g.fillRect(getRectangle().x, getRectangle().y, getRectangle().width, getRectangle().height);
//
//		// Loop that toggles between two frames for animation.
//		if (loop >= 10000) {
//			loop = 0;
//		}
//		if (loop % 6 == 0) {
//			segFrame = 1;
//		}
//		if (loop % 4 == 0 && loop % 3 == 0) {
//			segFrame = 0;
//		}
//
//		for (int i = segments.size() - 1; i >= 0; i--) {
//
//			OldSegment seg = segments.get(i);
//
//			if (seg.isHead()) {
//
//				if(seg.getYVel() < 0)
//					g.drawImage(headImg[1][segFrame], seg.getXPos(), seg.getYPos(), null);
//				else if(seg.getYVel() > 0)
//					g.drawImage(headImg[3][segFrame], seg.getXPos(), seg.getYPos(), null);
//				else {
//
//					if(seg.getXVel() < 0)
//						g.drawImage(headImg[2][segFrame], seg.getXPos(), seg.getYPos(), null);
//					else if(seg.getXVel() > 0)
//						g.drawImage(headImg[0][segFrame], seg.getXPos(), seg.getYPos(), null);
//				}
//			}
//			else {
//				if (seg.getYVel() != 0)
//					g.drawImage(segImg[1][segFrame], seg.getXPos(), seg.getYPos(), null);
//				else
//					g.drawImage(segImg[0][segFrame], seg.getXPos(), seg.getYPos(), null);
//			}
//		}
//
//		loop++;
//	}
//
//	private static BufferedImage segImg[][] = new BufferedImage[2][2];
//	private static BufferedImage headImg[][] = new BufferedImage[4][2];
//
//	public static void loadRes() {
//
//		BufferedImage head = ResourceUtil.loadInternalImage("main.res", "segHead.png");
//
//		headImg[0][0] = head;
//		headImg[0][1] = ResourceUtil.flipImage(headImg[0][0], false, true);
//
//		headImg[1][0] = ResourceUtil.rotateImage(headImg[0][0], Math.PI * (3d / 2d));
//		headImg[1][1] = ResourceUtil.flipImage(headImg[1][0], true, false);
//
//		headImg[2][0] = ResourceUtil.rotateImage(headImg[0][0], Math.PI);
//		headImg[2][1] = ResourceUtil.flipImage(headImg[2][0], false, true);
//
//		headImg[3][0] = ResourceUtil.rotateImage(headImg[0][0], Math.PI / 2);
//		headImg[3][1] = ResourceUtil.flipImage(headImg[3][0], true, false);
//
//		BufferedImage seg = ResourceUtil.loadInternalImage("main.res", "segment.png");
//
//		segImg[0][0] = seg;
//		segImg[0][1] = ResourceUtil.flipImage(segImg[0][0], false, true);
//		segImg[1][0] = ResourceUtil.rotateImage(segImg[0][0], Math.PI / 2);
//		segImg[1][1] = ResourceUtil.flipImage(segImg[1][0], true, false);
//
//	}
//
//	/**
//	 * Returns all the Square areas of the segments, to be handled by the game
//	 *
//	 * @return
//	 */
//	public ArrayList<OldSegment> getSegments() {
//		return segments;
//	}
//
//	@Override
//	public int getHealth() {
//		return segments.size();
//	}
//
//	@Override
//	public boolean isDead() {
//		if (segments.size() <= 0) {
//			return true;
//		}
//		return false;
//	}
}
