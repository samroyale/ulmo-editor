package rpg.editor.components;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import rpg.editor.Constants;
import rpg.editor.core.DisplayHelper;
import rpg.editor.core.SharedTileSelection;
import rpg.editor.core.TilePickerCanvas;
import rpg.editor.core.TileSelection;
import rpg.editor.model.Tile;

/**
 * Component that displays the last 10 tiles and allows the user to pick from
 * them as they can with the regular tile picker.
 * 
 * This class also implements the TileSelection interface itself and acts as a
 * proxy between the TilePicker and the SharedTileSelection.
 * 
 * @author seldred
 */
public class RecentTiles extends Composite implements TileSelection {

	private ScrolledComposite canvasHolder;
	private RecentTilesCanvas recentTilesCanvas;

	public RecentTiles(Composite parent) {
		this(parent, SWT.NONE);
	}

	public RecentTiles(Composite parent, int style) {
		super(parent, style);

		// layout setup
		setLayout(new GridLayout());
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// canvas
		canvasHolder = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		canvasHolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		recentTilesCanvas = new RecentTilesCanvas(canvasHolder);
		canvasHolder.setContent(recentTilesCanvas);
		canvasHolder.setExpandHorizontal(true);
		canvasHolder.setExpandVertical(true);

		// label
		Label tileLabel = new Label(this, SWT.CENTER);
		tileLabel.setText(Constants.NO_SELECTION_LABEL);
		tileLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		recentTilesCanvas.tileLabel = tileLabel;
	}

	public void tileSelected(Tile tile) {
		Rectangle rect = recentTilesCanvas.tileSelected(tile);
		canvasHolder.setMinSize(rect.width, rect.height);
		recentTilesCanvas.redraw();
	}

	public Tile getSelectedTile() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isTileSelected() {
		// TODO Auto-generated method stub
		return false;
	}
}

class RecentTilesCanvas extends TilePickerCanvas {

	private static final int MAX_TILES = 10;
	
	private TileSelection tileSelection = SharedTileSelection.getInstance();
	private List<Tile> tiles = new ArrayList<Tile>();

	protected Label tileLabel;

	public RecentTilesCanvas(Composite parent) {
		super(parent);
	}

	@Override
	public boolean isSelectionValid(Point tilePoint) {
		return true;
	}

	@Override
	public void tileSelectedAction() {
		tileSelection.tileSelected(tiles.get(selectedTile.x));
	}

	@Override
	public void setLabelText() {
		if (highlightTile == null) {
			tileLabel.setText(Constants.NO_SELECTION_LABEL);
		} else {
			tileLabel.setText(tiles.get(highlightTile.x).getName());
		}
	}

	public Rectangle updateTileImage() {
		if (tileImage != null) {
			tileImage.dispose();
		}
		tileImage = new Image(DisplayHelper.getDisplay(), tiles.size()
				* viewSize.getTileSize(), viewSize.getTileSize());
		GC gc = new GC(tileImage);
		int i = 0;
		for (Tile tile : tiles) {
			gc.drawImage(tile.getImage(), i * viewSize.getTileSize(), 0);
			i++;
		}
		gc.dispose();
		return tileImage.getBounds();
	}

	public Rectangle tileSelected(Tile tile) {
		// pass through to tile selection
		tileSelection.tileSelected(tile);
		// add to the list of tiles
		tiles.add(tile);
		if (tiles.size() > MAX_TILES) {
			tiles.remove(0);
		}
		// update tile image
		selectedTile = new Point(tiles.size() - 1, 0);
		return updateTileImage();
	}
}

/*
 * @Override public void dispose() { super.dispose(); for (Image image:
 * tileImages.values()) { image.dispose(); } }
 */
