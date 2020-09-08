package demo.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    EditText calcLineShow;
    EditText resultShow;
    static StringBuilder calcLine = new StringBuilder();
    static StringBuilder resultLine = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calcLineShow = findViewById(R.id.input_text);
        resultShow = findViewById(R.id.output_text);

        Button zero = findViewById(R.id.zero);
        Button nine = findViewById(R.id.nine);
        Button eight = findViewById(R.id.eight);
        Button seven = findViewById(R.id.seven);
        Button six = findViewById(R.id.six);
        Button five = findViewById(R.id.five);
        Button four = findViewById(R.id.four);
        Button three = findViewById(R.id.three);
        Button two = findViewById(R.id.two);
        Button one = findViewById(R.id.one);
        Button correct = findViewById(R.id.correct);
        Button divide = findViewById(R.id.divide);
        Button multiply = findViewById(R.id.multiply);
        Button subtract = findViewById(R.id.subtract);
        Button add = findViewById(R.id.add);
        Button equals = findViewById(R.id.equals);
        Button dot = findViewById(R.id.dot);

        zero.setOnClickListener(this);
        nine.setOnClickListener(this);
        eight.setOnClickListener(this);
        seven.setOnClickListener(this);
        six.setOnClickListener(this);
        five.setOnClickListener(this);
        four.setOnClickListener(this);
        three.setOnClickListener(this);
        two.setOnClickListener(this);
        one.setOnClickListener(this);
        correct.setOnClickListener(this);
        correct.setOnLongClickListener(this);
        divide.setOnClickListener(this);
        multiply.setOnClickListener(this);
        subtract.setOnClickListener(this);
        add.setOnClickListener(this);
        equals.setOnClickListener(this);
        dot.setOnClickListener(this);

    }

    public void setLine(String s) {
        calcLine.append(s);
        calcLineShow.setText(calcLine.toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zero:
                setLine("0");
                calculateBehind();
                break;
            case R.id.nine:
                setLine("9");
                calculateBehind();
                break;
            case R.id.eight:
                setLine("8");
                calculateBehind();
                break;
            case R.id.seven:
                setLine("7");
                calculateBehind();
                break;
            case R.id.six:
                setLine("6");
                calculateBehind();
                break;
            case R.id.five:
                setLine("5");
                calculateBehind();
                break;
            case R.id.four:
                setLine("4");
                calculateBehind();
                break;
            case R.id.three:
                setLine("3");
                calculateBehind();
                break;
            case R.id.two:
                setLine("2");
                calculateBehind();
                break;
            case R.id.one:
                setLine("1");
                calculateBehind();
                break;
            case R.id.correct:
                if (!calcLine.toString().isEmpty()) {
                    calcLine.delete(calcLine.length() - 1, calcLine.length());
                    calcLineShow.setText(calcLine.toString());
                    calculateBehind();
                    break;
                }
            case R.id.divide:
                if (!calcLine.toString().isEmpty())
                    setOperation("/");
                calculateBehind();
                break;
            case R.id.multiply:
                if (!calcLine.toString().isEmpty())
                    setOperation("*");
                calculateBehind();
                break;
            case R.id.subtract:
                if (calcLine.toString().isEmpty()) {
                    setLine("-");
                    calculateBehind();
                    break;
                } else if (calcLine.toString().substring(calcLine.length() - 1).equals("/") |
                        calcLine.toString().substring(calcLine.length() - 1).equals("*")) {
                    setLine("-");
                    calculateBehind();
                    break;
                } else {setOperation("-");
                    calculateBehind();
                break;}
            case R.id.add:
                if (!calcLine.toString().isEmpty())
                    setOperation("+");
                calculateBehind();
                break;
            case R.id.dot:
                if (!calcLine.toString().isEmpty()) {
                    setDot();
                    calculateBehind();
                    break;
                } else {setLine("0.");
                calculateBehind();
                break;}
            case R.id.equals:
                if (!calcLine.toString().isEmpty())
                    calculate();
                calculateBehind();
                break;
        }
    }

    private void setOperation(String s) {
        if (calcLine.toString().substring(calcLine.toString().length() - 1).equals(s)) {
        } else if (calcLine.toString().substring(calcLine.toString().length() - 1).equals("/") |
                calcLine.toString().substring(calcLine.toString().length() - 1).equals("*") |
                calcLine.toString().substring(calcLine.toString().length() - 1).equals("-") |
                calcLine.toString().substring(calcLine.toString().length() - 1).equals("+") |
                calcLine.toString().substring(calcLine.toString().length() - 1).equals(".")) {
            calcLine.delete(calcLine.toString().length() - 1, calcLine.toString().length());
            if (calcLine.toString().isEmpty()) {
                String completedString = calcLine.toString();
                calcLineShow.setText(completedString);
            } else {
                calcLine.append(s);
                String completedString = calcLine.toString();
                calcLineShow.setText(completedString);
            }
        } else {
            calcLine.append(s);
            String completedString = calcLine.toString();
            calcLineShow.setText(completedString);
        }
    }

    private void calculate() {
        try {
            String end = calcLine.toString();
            Expression ex = new ExpressionBuilder(end).build();
            BigDecimal d = BigDecimal.valueOf(ex.evaluate());
            calcLine.delete(0, calcLine.toString().length());
            String completedString = d.toString();
            calcLine.append(completedString);
            if (calcLine.substring(calcLine.toString().length() - 1, calcLine.toString().length()).equals("0") &
                    calcLine.substring(calcLine.toString().length() - 2, calcLine.toString().length() - 1).equals(".")) {
                calcLine.delete(calcLine.toString().length() - 2, calcLine.toString().length());
            }
            calcLineShow.setText(calcLine.toString());
            resultShow.setText("");
        } catch (ArithmeticException exception0) {
            Toast.makeText(getApplicationContext(), "НА 0 ДЕЛИТЬ НЕЛЬЗЯ",
                    Toast.LENGTH_LONG).show();
        } catch (Exception anyExceptions) {
            Toast.makeText(getApplicationContext(), "НЕВОЗМОЖНО ПРОИЗВЕСТИ ПОДСЧЁТ",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void calculateBehind() {
        if (calcLineShow.toString().isEmpty()) {
            resultShow.setText(calcLine.toString());
        } else if(calcLine.toString().contains("/")|calcLine.toString().contains("*")|calcLine.toString().contains("-")|calcLine.toString().contains("+")){
            try {
                Expression varCalc = new ExpressionBuilder(calcLine.toString()).build();
                BigDecimal d = BigDecimal.valueOf(varCalc.evaluate());
                resultLine.delete(0, resultLine.toString().length());
                String completedString = d.toString();
                resultLine.append(completedString);
                if (resultLine.substring(resultLine.toString().length() - 1, resultLine.toString().length()).equals("0") &
                        resultLine.substring(resultLine.toString().length() - 2, resultLine.toString().length() - 1).equals(".")) {
                    resultLine.delete(resultLine.toString().length() - 2, resultLine.toString().length());
                }
                resultShow.setText(resultLine.toString());
            } catch (Exception anyExceptions) {
                resultShow.setText("");
            }
        }
    }

    private void setDot() {
        if (calcLine.toString().length() == 1 & !calcLine.toString().equals("-")) {
            setLine(".");
        } else if (calcLine.toString().length() == 2) {
            if (!calcLine.toString().substring(1, 2).equals(".")) {
                setLine(".");
            }
        } else if (calcLine.toString().length() > 2) {
            for (int i = calcLine.toString().length(); i > 1; i--) {
                if (calcLine.toString().substring(i - 1, i).equals(".")) {
                    break;
                } else if (calcLine.toString().substring(i - 1, i).equals("/") |
                        calcLine.toString().substring(i - 1, i).equals("*") |
                        calcLine.toString().substring(i - 1, i).equals("-") |
                        calcLine.toString().substring(i - 1, i).equals("+")) {
                    setLine(".");
                    break;
                } else if (i == 2) {
                    setLine(".");
                    break;
                }
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (view.getId() == R.id.correct) {
            calcLine.delete(0, calcLine.length());
            calcLineShow.setText(calcLine.toString());
            return true;
        }
        return false;
    }
}