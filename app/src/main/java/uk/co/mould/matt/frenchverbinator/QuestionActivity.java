package uk.co.mould.matt.frenchverbinator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import uk.co.mould.matt.frenchverbinator.showcase.AutolaunchingQuestionViewShowcaser;
import uk.co.mould.matt.frenchverbinator.showcase.ShowcaseViewAdapter;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AndroidQuestionView questionView = (AndroidQuestionView) findViewById(R.id.android_question_view);
        QuestionPresenterFactory.create(getApplicationContext(), questionView);

        QuestionActivityShowcaserBuilder.build(this, questionView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private static class QuestionActivityShowcaserBuilder {
        static AutolaunchingQuestionViewShowcaser build(Activity activity, AndroidQuestionView questionView) {
            ShowcaseView showcaseView = new ShowcaseView.Builder(activity)
                    .setStyle(R.style.CustomShowcaseTheme)
                    .singleShot((long)R.id.question_activity_showcase_id)
                    .build();
            showcaseView.setDetailTextAlignment(Layout.Alignment.ALIGN_CENTER);
            AutolaunchingQuestionViewShowcaser.ToolbarTargetFactory toolbarTargetFactory = new AutolaunchingQuestionViewShowcaser.ToolbarTargetFactory() {
                @Override
                public Target createToolbarTarget(Toolbar toolbar, int targetId) {
                    return new ToolbarActionItemTarget(toolbar, targetId);
                }
            };
            AutolaunchingQuestionViewShowcaser.ViewTargetFactory viewTargetFactory = new AutolaunchingQuestionViewShowcaser.ViewTargetFactory() {
                @Override
                public Target createTarget(View targetView) {
                    return new ViewTarget(targetView);
                }
            };
            return new AutolaunchingQuestionViewShowcaser(toolbarTargetFactory, viewTargetFactory, new AMLShowcaseViewAdapter(showcaseView), questionView);
        }
    }


    private static class AMLShowcaseViewAdapter implements ShowcaseViewAdapter {
        private ShowcaseView showcaseView;

        public AMLShowcaseViewAdapter(ShowcaseView showcaseView) {
            this.showcaseView = showcaseView;
        }

        @Override
        public void hide() {
            showcaseView.hide();
        }

        @Override
        public void overrideButtonClick(View.OnClickListener listener) {
            showcaseView.overrideButtonClick(listener);
        }

        @Override
        public void setContentTextForView(String contentText, Target view) {
            showcaseView.setContentText(contentText);
            showcaseView.setTarget(view);
        }
    }

    public static class ToolbarActionItemTarget implements Target {

        private final Toolbar toolbar;
        private final int menuItemId;

        public ToolbarActionItemTarget(Toolbar toolbar, int itemId) {
            this.toolbar = toolbar;
            this.menuItemId = itemId;
        }

        @Override
        public Point getPoint() {
            return new ViewTarget(toolbar.findViewById(menuItemId)).getPoint();
        }

    }
}
