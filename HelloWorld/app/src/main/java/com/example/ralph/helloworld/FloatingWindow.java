package com.example.ralph.helloworld;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by ralph on 2017/6/4.
 */

public class FloatingWindow extends Service {

    private WindowManager windowManager;
    private LinearLayout mainContent;
    private WindowManager.LayoutParams mainParameter;
    private TextView question;
    private LinearLayout borderLayer;
    private WindowManager.LayoutParams borderParameter;

    private LinearLayout leftButton;
    private WindowManager.LayoutParams leftParameter;
    private TextView leftChoice;
    private LinearLayout rightButton;
    private WindowManager.LayoutParams rightInstallParameter;
    private WindowManager.LayoutParams rightCoverParameter;
    private TextView rightChoice;
    private TextView rightChoiceFull;
    private LinearLayout rightBackground;
    private LinearLayout middleBorder;
    private WindowManager.LayoutParams middleBorderParameter;

    private TextView correctScore;
    private TextView incorrectScore;
    private int correctCount;
    private int incorrectCount;


    private int count;
    private int questionNumber;
    private boolean installed;
    final private String[] questionBank = {
            "問題：鯨魚是靠什麼呼吸？",
            "問題：化學元素「金」的符號是：",
    };
    final private String[] leftChoiceBank = {
            "腮",
            "Au",
    };
    final private String[] rightChoiceBank = {
            "肺",
            "Ar",
    };
    final private int[] answer = {
            1,
            0,
    };

    final int alpha = 255;
    final int backgroundColor = Color.argb(alpha, 238, 238, 238);
    final int borderColor = Color.argb(alpha, 209, 209, 209);
    //final int backgroundColor = Color.argb(128, 100, 100, 100);
    final int textColor = Color.argb(alpha, 0, 150, 136);

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setWindowManager() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    }

    private void setMainContent() {
        mainContent = new LinearLayout(this);
        LinearLayout.LayoutParams llParameters =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
        mainContent.setBackgroundColor(backgroundColor);
        mainContent.setOrientation(LinearLayout.VERTICAL);
        mainContent.setLayoutParams( llParameters );
        mainContent.setGravity(Gravity.CENTER);
    }

    private void setMainParameter() {
        mainParameter =
            new WindowManager.LayoutParams( 1024, 1555,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT );
        mainParameter.width = WindowManager.LayoutParams.MATCH_PARENT;
        mainParameter.x = 0;
        mainParameter.y = -73;
        mainParameter.gravity = Gravity.CENTER | Gravity.TOP;
    }

    private void setBorderLayer() {
        borderLayer = new LinearLayout(this);
        LinearLayout.LayoutParams llParameters =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
        borderLayer.setBackgroundColor(borderColor);
        borderLayer.setLayoutParams( llParameters );
        borderLayer.setGravity(Gravity.CENTER);

    }

    private void setBorderParameter() {
        borderParameter =
            new WindowManager.LayoutParams( 1024, 1557,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT );
        borderParameter.width = WindowManager.LayoutParams.MATCH_PARENT;
        borderParameter.x = 0;
        borderParameter.y = -72;
        borderParameter.gravity = Gravity.CENTER;
    }

    private void setQuestion(String text) {
        question = new TextView(this);
        question.setText(text);
        ViewGroup.LayoutParams textParameter = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        question.setLayoutParams(textParameter);
        question.setTextColor(textColor);
        question.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
    }

    private void setLeftButton() {
        leftButton = new LinearLayout(this);
        leftButton.setBackgroundColor(backgroundColor);
        //leftButton.setBackgroundColor(Color.argb(128, 128, 128 ,128));
        LinearLayout.LayoutParams llParameters =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
        leftButton.setLayoutParams(llParameters);
        leftButton.setGravity(Gravity.CENTER);
    }

    private void setLeftParameter() {
        leftParameter =
            new WindowManager.LayoutParams( 539, 144,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT );
        leftParameter.x = -270;
        leftParameter.y = 800;
        leftParameter.gravity = Gravity.CENTER;
    }

    private void setLeftChoice(String text) {
        leftChoice = new TextView(this);
        leftChoice.setText(text);
        ViewGroup.LayoutParams textParameter = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        leftChoice.setLayoutParams(textParameter);
        leftChoice.setTextColor(textColor);
        leftChoice.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        //leftChoice.setTypeface(null, Typeface.BOLD);
    }

    private void setMiddleBorder() {
        middleBorder = new LinearLayout(this);
        middleBorder.setBackgroundColor(borderColor);
        LinearLayout.LayoutParams llParameters =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
        middleBorder.setLayoutParams(llParameters);
    }

    private void setMiddleBorderParameter() {
        middleBorderParameter =
            new WindowManager.LayoutParams( 541, 144,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT );
        middleBorderParameter.x = -270;
        middleBorderParameter.y = 800;
        middleBorderParameter.gravity = Gravity.CENTER;
    }

    private void setRightButton() {
        rightButton = new LinearLayout(this);
        rightButton.setBackgroundColor(backgroundColor);
        LinearLayout.LayoutParams llParameters =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
        rightButton.setLayoutParams(llParameters);
        rightButton.setGravity(Gravity.CENTER);
    }

    private void setRightInstallParameter() {
        rightInstallParameter =
                new WindowManager.LayoutParams( 270, 90,
                        WindowManager.LayoutParams.TYPE_PHONE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                        PixelFormat.TRANSLUCENT );
        //rightParameter.width = WindowManager.LayoutParams.MATCH_PARENT;
        rightInstallParameter.x = 270;
        rightInstallParameter.y = 775;
        rightInstallParameter.gravity = Gravity.CENTER;
    }

    private void setRightCoverParameter() {
        rightCoverParameter =
                new WindowManager.LayoutParams( 539, 144,
                        WindowManager.LayoutParams.TYPE_PHONE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        PixelFormat.TRANSLUCENT );
        rightCoverParameter.x = 270;
        rightCoverParameter.y = 800;
        rightCoverParameter.gravity = Gravity.CENTER;
    }

    private void setRightChoice(String text) {
        rightChoice = new TextView(this);
        rightChoice.setText(text);
        ViewGroup.LayoutParams textParameter = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rightChoice.setLayoutParams(textParameter);
        rightChoice.setTextColor(textColor);
        rightChoice.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
    }

    private void setRightChoiceFull(String text) {
        rightChoiceFull = new TextView(this);
        rightChoiceFull.setText(text);
        ViewGroup.LayoutParams textParameter = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rightChoiceFull.setLayoutParams(textParameter);
        rightChoiceFull.setTextColor(textColor);
        rightChoiceFull.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
    }

    private void setRightBackground() {
        rightBackground = new LinearLayout(this);
        rightBackground.setBackgroundColor(backgroundColor);
        LinearLayout.LayoutParams llParameters =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
        rightBackground.setLayoutParams(llParameters);
        rightBackground.setGravity(Gravity.CENTER);
    }

    private void setScoreBoard() {
        correctCount = 0;
        incorrectCount = 0;
        correctScore = new TextView(this);
        incorrectScore = new TextView(this);
        correctScore.setText("正確: " + correctCount);
        incorrectScore.setText("錯誤: " + incorrectCount);
        RelativeLayout.LayoutParams textParameter = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        correctScore.setLayoutParams(textParameter);
        incorrectScore.setLayoutParams(textParameter);
        correctScore.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        incorrectScore.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        correctScore.setTextColor(textColor);
        incorrectScore.setTextColor(textColor);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createQuestion();
    }

    public void updateQuestion(int buttonPressed) {
        Log.e("update:", "" + buttonPressed);
        if(buttonPressed == 0) {
            if(answer[questionNumber] == 0) {
                correctCount += 1;
                correctScore.setText("正確: " + correctCount);
            }
            else{
                incorrectCount += 1;
                incorrectScore.setText("錯誤: " + incorrectCount);
            }
            questionNumber = (questionNumber + 1) % questionBank.length;
            question.setText(questionBank[questionNumber]);
            leftChoice.setText(leftChoiceBank[questionNumber]);
            rightChoice.setText(rightChoiceBank[questionNumber]);
            rightChoiceFull.setText(rightChoiceBank[questionNumber]);
        }
        else if(buttonPressed == 1){
            if(answer[questionNumber] == 1) {
                correctCount += 1;
                correctScore.setText("正確: " + correctCount);
            }
            else{
                incorrectCount += 1;
                incorrectScore.setText("錯誤: " + incorrectCount);
            }
            questionNumber = (questionNumber + 1) % questionBank.length;
            question.setText(questionBank[questionNumber]);
            leftChoice.setText(leftChoiceBank[questionNumber]);
            rightChoice.setText(rightChoiceBank[questionNumber]);
            rightChoiceFull.setText(rightChoiceBank[questionNumber]);
        }
        else if(buttonPressed == 2){
            if(answer[questionNumber] == 1) {
                correctCount += 1;
                correctScore.setText("正確: " + correctCount);
            }
            else{
                incorrectCount += 1;
                incorrectScore.setText("錯誤: " + incorrectCount);
            }
            questionNumber = (questionNumber + 1) % questionBank.length;
            question.setText(questionBank[questionNumber]);
            leftChoice.setText(leftChoiceBank[questionNumber]);
            rightChoice.setText(rightChoiceBank[questionNumber]);
            rightChoiceFull.setText(rightChoiceBank[questionNumber]);
            if(!installed) {
                windowManager.removeView(rightBackground);
                installed = true;
            }
            else {
                if(rightBackground.getParent() == null) {
                    windowManager.addView(rightBackground, rightCoverParameter);
                }
                else if(rightBackground.getParent() != null && rightButton.getParent() != null) {
                    windowManager.removeView(rightButton);
                }
            }
        }
    }


    public void createQuestion() {
        count = 0;
        installed = false;
        questionNumber = 0;
        setWindowManager();
        setMainContent();
        setMainParameter();
        setQuestion(questionBank[questionNumber]);
        setBorderLayer();
        setBorderParameter();
        setLeftButton();
        setLeftParameter();
        setLeftChoice(leftChoiceBank[questionNumber]);
        setMiddleBorder();
        setMiddleBorderParameter();
        setRightButton();
        setRightCoverParameter();
        setRightInstallParameter();
        setRightBackground();
        setRightChoice(rightChoiceBank[questionNumber]);
        setRightChoiceFull(rightChoiceBank[questionNumber]);
        setScoreBoard();
        mainContent.addView(correctScore);
        mainContent.addView(incorrectScore);
        mainContent.addView(question);
        windowManager.addView(borderLayer, borderParameter);
        windowManager.addView(mainContent, mainParameter);
        windowManager.addView(middleBorder, middleBorderParameter);
        leftButton.addView(leftChoice);
        windowManager.addView(leftButton, leftParameter);
        windowManager.addView(rightBackground, rightCoverParameter);
        rightButton.addView(rightChoice);
        rightBackground.addView(rightChoiceFull);
        windowManager.addView(rightButton, rightInstallParameter);
        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                int x = (int) event.getX();
                int y = (int) event.getY();
                Log.e("left onTouchListener", "User touch at X:" + x + " Y:" + y);
                if(y >= 0) {
                    if(x == 0) {
                        // pressed through right button
                        Intent intent = new Intent(v.getContext(), FloatingWindow.class);
                        intent.putExtra("pressed", 2);
                        v.getContext().startService(intent);
                    }
                    else if(x > 541) {
                        // pressed at right button
                        Intent intent = new Intent(v.getContext(), FloatingWindow.class);
                        intent.putExtra("pressed", 1);
                        v.getContext().startService(intent);

                    }
                    else if(x > 0 && x < 539) {
                        // pressed left button
                        Intent intent = new Intent(v.getContext(), FloatingWindow.class);
                        intent.putExtra("pressed", 0);
                        v.getContext().startService(intent);
                    }

                }
                return false;
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null && intent.hasExtra("pressed")) {
            updateQuestion(intent.getIntExtra("pressed", 0));
            count += 1;
            if(count == 10) {
                windowManager.removeView(borderLayer);
                windowManager.removeView(mainContent);
                windowManager.removeView(leftButton);
                if(rightBackground.getParent() != null)
                    windowManager.removeView(rightBackground);
                if(rightButton.getParent() != null)
                    windowManager.removeView(rightButton);
                windowManager.removeView(middleBorder);
                stopSelf();
                return -1;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
