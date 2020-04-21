package isel.poo.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    public static String FILENAME = "counter.txt";
    Button inc, dec, reset, load, save;
    TextView counter;

    CounterView view;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        counter = new TextView(this);
        counter.setText("0");
        counter.setTextSize(100);
        counter.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        inc = new Button(this);
        inc.setText("+");

        dec = new Button(this);
        dec.setText("-");

        reset = new Button(this);
        reset.setText("Reset");

        load = new Button(this);
        load.setText("Load");

        save = new Button(this);
        save.setText("Save");

        view = new CounterView(this);
        view.setListener(new CounterView.Listener() {
            @Override
            public void valueChanged(int value) {
                clear();
                add(value);
            }
        });

        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        LinearLayout buttons = new LinearLayout(this);
        buttons.setOrientation(LinearLayout.HORIZONTAL);

        buttons.addView(inc);
        buttons.addView(dec);
        buttons.addView(reset);
        buttons.addView(load);
        buttons.addView(save);
        main.addView(buttons);
        main.addView(counter);
        main.addView(view);

        setContentView(main);

        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(+1);
                movedCircle(getValue());
            }
        });
        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(-1);
                movedCircle(getValue());
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                movedCircle(0);
            }
        });
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void load() {
        try (Scanner in = new Scanner(openFileInput(FILENAME))) {
            clear();
            add(in.nextInt());
            view.setValue(in.nextInt());
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private void save() {
        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(openFileOutput(FILENAME,MODE_PRIVATE))
        )) {
            pw.println(getValue());
            pw.println(view.getCounterValue());
        } catch (Exception e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private void movedCircle(int v) {
        view.setValue(v);
    }

    private void clear(){
        counter.setText("0");
    }

    private void add(int value){
        int v = getValue() + value;
        counter.setText(Integer.toString(v));
    }

    private int getValue() {
        return Integer.parseInt(counter.getText().toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt("counter", getValue());
        state.putInt("counterValue", view.getCounterValue());
    }

    @Override
    protected  void onRestoreInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        add(state.getInt("counter"));
        view.setValue(state.getInt("counterValue"));
    }
}
