/**
 * 
 */
package com.insigma.commons.editorframework.graphic.editor;

import org.eclipse.swt.SWT;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Administrator
 *
 */
public class MapItemTest {

	public static void main(String[] args) {
		System.out.println(SWT.CTRL & SWT.CTRL);
	}

	@Before
	public void setup() {

	}

	@Test
	public void testSetDataState() {

		{//new state first
			MapItem root = new MapItem(13);
			assertTrue(root.getDataState() instanceof NewState);

			MapItem child1 = new MapItem(131);
			root.getDataState().add(child1);

			assertTrue(root.getDataState() instanceof NewState);
			root.getDataState().update();
			assertTrue(root.getDataState() instanceof NewState);
			root.getDataState().delete(child1);
			assertTrue(root.getDataState() instanceof NewState);

		}
		{//modify state first
			MapItem root = new MapItem(13);
			root.changeToSaveState();
			root.getDataState().update();//change root to modify

			MapItem mapItem131 = new MapItem(131);
			root.getDataState().add(mapItem131);

			assertTrue(root.getDataState() instanceof ModifyState);
			root.getDataState().update();
			assertTrue(root.getDataState() instanceof ModifyState);
			root.getDataState().delete(mapItem131);
			assertTrue(root.getDataState() instanceof SaveState);
		}
		{//save state first
			MapItem root = new MapItem(1);
			{
				root.addMapItem(new MapItem(11));
				root.addMapItem(new MapItem(12));
			}
			root.changeToSaveState();//change root to save

			MapItem mapItem131 = new MapItem(131);
			root.getDataState().add(mapItem131);

			assertTrue(root.getDataState() instanceof ModifyState);
			root.getDataState().update();
			assertTrue(root.getDataState() instanceof ModifyState);
			root.getDataState().delete(mapItem131);
			assertTrue(root.getDataState() instanceof SaveState);

			root.getDataState().add(mapItem131);

			assertTrue(root.getDataState() instanceof ModifyState);

			MapItem mapItem132 = new MapItem(132);
			root.getDataState().add(mapItem132);
			assertTrue(root.getDataState() instanceof ModifyState);//add 132

			MapItem mapItem133 = new MapItem(133);
			root.getDataState().add(mapItem133);
			assertTrue(root.getDataState() instanceof ModifyState);//add 133

			root.getDataState().delete(mapItem131);
			assertTrue(root.getDataState() instanceof ModifyState);//delete 131

			root.getDataState().delete(mapItem133);
			assertTrue(root.getDataState() instanceof ModifyState);//delete 131

			root.getDataState().delete(mapItem132);
			assertTrue(root.getDataState() instanceof SaveState);
		}
	}

}
