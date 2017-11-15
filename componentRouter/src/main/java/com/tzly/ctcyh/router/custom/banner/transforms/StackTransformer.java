package com.tzly.ctcyh.router.custom.banner.transforms;

import android.view.View;

public class StackTransformer extends ABaseTransformer {

	@Override
	protected void onTransform(View view, float position) {
		view.setTranslationX(position < 0 ? 0f : -view.getWidth() * position);
	}

}
