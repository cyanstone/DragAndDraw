package com.example.draganddraw;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

public class DragAndDrawActivity extends SingleFragmentActivity {

	@Override
	protected Fragment creatFragment() {
	
		return new DragAndDrawFragment();
	}

	
}
