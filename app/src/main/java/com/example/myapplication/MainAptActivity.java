package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import androidx.room.Room;
import android.os.Bundle;
import android.util.Log;
import android.graphics.Color;



public class MainAptActivity extends AppCompatActivity {
    private static final String TAG = "MainAptActivity";

    private LinearLayout linearLayoutInfos;
    private LayoutInflater inflater;
    private List<String> dataList = new ArrayList<>();
    private ArrayAdapter<String> regionSpinnerAdapter;
    private ArrayAdapter<String> sigunguSpinnerAdapter;
    private Spinner regionSpinner;
    private Spinner sigunguSpinner;
    private RadioGroup radioGroup;
    private RadioButton radioButtonApt;
    private RadioButton radioButtonHouse;
    private EditText yhighestCost;
    private EditText ylowestCost;
    private EditText dhighestCost;
    private EditText dlowestCost;
    private LinearLayout resultLayout;
    private EditText namecall;
    private String kind;

    int yhighestPrice_v=0;
    int ylowestPrice_v=0;
    int dhighestPrice_v=0;
    int dlowestPrice_v=0;

    private SharedPreferences SPreferences1;
    private final String NameSPreferences1 = "Day";
    private String strSDFormatDay1 = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aptmain);
        linearLayoutInfos = findViewById(R.id.LayoutScroll);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //강원도 선택 배열
        regionSpinner = findViewById(R.id.region_array);
        //춘천시 선택 배열
        sigunguSpinner = findViewById(R.id.sigungu_array);
        radioGroup = findViewById(R.id.radioGroup);
        radioButtonApt = findViewById(R.id.radioButtonApt);
        radioButtonHouse = findViewById(R.id.radioButtonHouse);
        yhighestCost = findViewById(R.id.yh_cost);
        ylowestCost = findViewById(R.id.yl_cost);
        dhighestCost = findViewById(R.id.dh_cost);
        dlowestCost = findViewById(R.id.dl_cost);
        resultLayout = findViewById(R.id.LayoutScroll);
        namecall=findViewById(R.id.search_name);

        long CurrentTime = System.currentTimeMillis(); // 현재 시간을 msec 단위로 얻음
        Date TodayDate = new Date(CurrentTime); // 현재 시간 Date 변수에 저장
        SimpleDateFormat SDFormat = new SimpleDateFormat("dd");
        strSDFormatDay1 = SDFormat.format(TodayDate); // 'dd' 형태로 포맷 변경

        // SharedPreferences 획득
        SPreferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        String strSPreferencesDay1 = SPreferences1.getString(NameSPreferences1, "0");
        // 공지사항 알림창 띄움
        if((Integer.parseInt(strSDFormatDay1) - Integer.parseInt(strSPreferencesDay1)) != 0)
            StartMainAlertDialog();


        readDataFromCSV();
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "kang.db")
                .createFromAsset("database/kang.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        // Spinner 데이터 초기화
        initSpinners();

        // 검색 버튼 클릭 이벤트 처리
        Button searchButton = findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedRegion = regionSpinner.getSelectedItem().toString();
                String selectedSigungu = sigunguSpinner.getSelectedItem().toString();
                boolean isAptSelected = radioButtonApt.isChecked();
                boolean isHouseSelected = radioButtonHouse.isChecked();
                String yhighestPrice = yhighestCost.getText().toString();
                String ylowestPrice = ylowestCost.getText().toString();
                String dhighestPrice = dhighestCost.getText().toString();
                String dlowestPrice = dlowestCost.getText().toString();

                String sel;
                //이름검색창의 텍스트
                String enteredText = namecall.getText().toString();
                Log.d("TAG", "Entered Text: " + enteredText);
                resultLayout.removeAllViews();

                    if (isAptSelected) {
                        kind = "아파트";
                    } else if (isHouseSelected) {
                        kind = "빌라/주택";
                    }
                    ///실험코드
                    if(sigunguSpinner.getSelectedItem().toString().equals("전체")){
                        sel="";
                        Log.d("MyApp", "전체 sel " + sel);
                    }else{
                        sel=sigunguSpinner.getSelectedItem().toString();
                        Log.d("MyApp", "지역 sels: " + sel);
                    }

                    int dhcs=9999999;
                    int dlcs=0;
                    int lhcs=9999999;
                    int llcs=0;


                    if (!yhighestPrice.equals("")) {
                        //lhcs=9999999;
                        lhcs=Integer.parseInt(yhighestPrice);
                        Log.d("MyApp", "llcs 입력값 없을때입니다 " + llcs);
                    }
                    if(!ylowestPrice.equals("")){
                        //llcs=1;
                        llcs =Integer.parseInt(ylowestPrice);

                        Log.d("MyApp", "lhcs 입력값 없을때입니다 " + lhcs);
                    }
                    if (!dhighestPrice.equals("")) {
                        //dhcs=9999999;
                        dhcs=Integer.parseInt(dhighestPrice);
                    }
                    if(!dlowestPrice.equals("")){
                        //dlcs=1;
                        dlcs=Integer.parseInt(dlowestPrice);
                    }
                    //조건값 확인
                    Log.d("MyApp", "kind " + kind);
                    Log.d("MyApp", "name " + enteredText);
                    Log.d("MyApp", "강원도 " + regionSpinner.getSelectedItem().toString());
                    Log.d("MyApp", "춘천시 " +sel);
                    Log.d("MyApp", "고매 " + dhcs);
                    Log.d("MyApp", "소매 " + dlcs);
                    Log.d("MyApp", "고전 " + lhcs);
                    Log.d("MyApp", "소전 " +llcs);

                if (enteredText.equals("")) {
                    //Log.d("MyApp", "입력값 있을떄 실행: ");
                     //List<Todo> allTodos = db.todoDao().getByName(kind,enteredText,regionSpinner.getSelectedItem().toString(),sel);
                    List<Todo> allTodos = db.todoDao().getByName(kind,regionSpinner.getSelectedItem().toString(),sel,dhcs,dlcs,lhcs,llcs);

                    StringBuilder namesStringBuilder = new StringBuilder();
                    Log.d("MyApp", "All Todos: " + allTodos.toString());


                    for (Todo todo : allTodos) {
                        namesStringBuilder.append(todo.getLatitude() + " ").append(todo.getLongitude() + " ").append(todo.getName() + " ").append(todo.getGu() + " ").append(todo.getDong() + " ").append("\n");
                        //Log.d("MyApp", "colnum: " + todo.getColnum());

                        ViewGroup apt_info = (ViewGroup) inflater.inflate(R.layout.activity_apt_info, linearLayoutInfos, false);
                        ((TextView) apt_info.findViewById(R.id.address)).setText(todo.getSi() + " " + todo.getGu() + " "+todo.getDong());
                        ((TextView) apt_info.findViewById(R.id.textView_name)).setText(todo.getName() + "(" + todo.getType() + ")");

                        if((Math.round(todo.getLrate()*100 * 10.0) / 10.0)>60.0){
                            ((TextView) apt_info.findViewById(R.id.textView_rate)).setText(Double.toString(Math.round(todo.getLrate()*100 * 10.0) / 10.0) + "%");
                            ((TextView) apt_info.findViewById(R.id.textView_rate)).setTextColor(Color.RED);
                        } else if ((Math.round(todo.getLrate()*100 * 10.0) / 10.0)<=60.0 && (Math.round(todo.getLrate()*100 * 10.0) / 10.0)>=45.0){
                            ((TextView) apt_info.findViewById(R.id.textView_rate)).setText(Double.toString(Math.round(todo.getLrate()*100 * 10.0) / 10.0) + "%");
                            ((TextView) apt_info.findViewById(R.id.textView_rate)).setTextColor(Color.BLUE);
                        }
                        else if ((Math.round(todo.getLrate()*100 * 10.0) / 10.0)<45.0 &&(Math.round(todo.getLrate()*100 * 10.0) / 10.0)>0.0){
                            ((TextView) apt_info.findViewById(R.id.textView_rate)).setText(Double.toString(Math.round(todo.getLrate()*100 * 10.0) / 10.0) + "%");
                            ((TextView) apt_info.findViewById(R.id.textView_rate)).setTextColor(Color.GREEN);
                        }

                        else{
                            ((TextView) apt_info.findViewById(R.id.textView_rate)).setText(Double.toString(Math.round(todo.getLrate()*100 * 10.0) / 10.0) + "%");

                        }


                        ((TextView) apt_info.findViewById(R.id.dealH_value)).setText(Integer.toString(todo.getMaxdealprice()));
                        ((TextView) apt_info.findViewById(R.id.dealL_value)).setText(Integer.toString(todo.getMindealprice()));
                        ((TextView) apt_info.findViewById(R.id.yearH_value)).setText(Integer.toString(todo.getMaxlprice()));
                        ((TextView) apt_info.findViewById(R.id.yearL_value)).setText(Integer.toString(todo.getMinlprice()));

                        ((TextView) apt_info.findViewById(R.id.dealing_value1)).setText(Integer.toString(todo.getReal1dealprice()));
                        ((TextView) apt_info.findViewById(R.id.dealing_date1)).setText(Integer.toString(todo.getReal1year()) + "." + Integer.toString(todo.getReal1month()));

                        ((TextView) apt_info.findViewById(R.id.dealing_value2)).setText(Integer.toString(todo.getReal2dealprice()));
                        ((TextView) apt_info.findViewById(R.id.dealing_date2)).setText(Integer.toString(todo.getReal2year()) + "." + Integer.toString(todo.getReal2month()));


                        ((TextView) apt_info.findViewById(R.id.dealing_value3)).setText(Integer.toString(todo.getReal3dealprice()));
                        ((TextView) apt_info.findViewById(R.id.dealing_date3)).setText(Integer.toString(todo.getReal3year()) + "." + Integer.toString(todo.getReal3month()));


                        linearLayoutInfos.addView(apt_info, 0);

                    }

                }
                else{
                    List<Todo> allTodos = db.todoDao().getBYALlcon(kind,enteredText,regionSpinner.getSelectedItem().toString(),sel,dhcs,dlcs,lhcs,llcs);

                    StringBuilder namesStringBuilder = new StringBuilder();
                    Log.d("MyApp", "All Todos: " + allTodos.toString());
                    for (Todo todo : allTodos) {
                        namesStringBuilder.append(todo.getLatitude() + " ").append(todo.getLongitude() + " ").append(todo.getName() + " ").append(todo.getGu() + " ").append(todo.getDong() + " ").append("\n");
                        Log.v("MyApp", "  정보: " + todo.getColnum());


                        ViewGroup apt_info = (ViewGroup) inflater.inflate(R.layout.activity_apt_info, linearLayoutInfos, false);
                        ((TextView) apt_info.findViewById(R.id.address)).setText(todo.getSi() + " " + todo.getGu() + " "+todo.getDong());
                        ((TextView) apt_info.findViewById(R.id.textView_name)).setText(todo.getName() + "(" + todo.getType() + ")");

                        if((Math.round(todo.getLrate()*100 * 10.0) / 10.0)>60.0){
                            ((TextView) apt_info.findViewById(R.id.textView_rate)).setText(Double.toString(Math.round(todo.getLrate()*100 * 10.0) / 10.0) + "%");
                            ((TextView) apt_info.findViewById(R.id.textView_rate)).setTextColor(Color.RED);
                        } else if ((Math.round(todo.getLrate()*100 * 10.0) / 10.0)<=60.0 && (Math.round(todo.getLrate()*100 * 10.0) / 10.0)>=45.0){
                            ((TextView) apt_info.findViewById(R.id.textView_rate)).setText(Double.toString(Math.round(todo.getLrate()*100 * 10.0) / 10.0) + "%");
                            ((TextView) apt_info.findViewById(R.id.textView_rate)).setTextColor(Color.BLUE);
                        }
                        else if ((Math.round(todo.getLrate()*100 * 10.0) / 10.0)<45.0 &&(Math.round(todo.getLrate()*100 * 10.0) / 10.0)>0.0){
                            ((TextView) apt_info.findViewById(R.id.textView_rate)).setText(Double.toString(Math.round(todo.getLrate()*100 * 10.0) / 10.0) + "%");
                            ((TextView) apt_info.findViewById(R.id.textView_rate)).setTextColor(Color.GREEN);
                        }

                        else{
                           ((TextView) apt_info.findViewById(R.id.textView_rate)).setText(Double.toString(Math.round(todo.getLrate()*100 * 10.0) / 10.0) + "%");
                           // ((TextView) apt_info.findViewById(R.id.textView_rate)).setText(todo.getColnum());

                        }


                        ((TextView) apt_info.findViewById(R.id.dealH_value)).setText(Integer.toString(todo.getMaxdealprice()));
                        ((TextView) apt_info.findViewById(R.id.dealL_value)).setText(Integer.toString(todo.getMindealprice()));
                        ((TextView) apt_info.findViewById(R.id.yearH_value)).setText(Integer.toString(todo.getMaxlprice()));
                        ((TextView) apt_info.findViewById(R.id.yearL_value)).setText(Integer.toString(todo.getMinlprice()));

                        ((TextView) apt_info.findViewById(R.id.dealing_value1)).setText(Integer.toString(todo.getReal1dealprice()));
                        ((TextView) apt_info.findViewById(R.id.dealing_date1)).setText(Integer.toString(todo.getReal1year()) + "." + Integer.toString(todo.getReal1month()));

                        ((TextView) apt_info.findViewById(R.id.dealing_value2)).setText(Integer.toString(todo.getReal2dealprice()));
                        ((TextView) apt_info.findViewById(R.id.dealing_date2)).setText(Integer.toString(todo.getReal2year()) + "." + Integer.toString(todo.getReal2month()));


                        ((TextView) apt_info.findViewById(R.id.dealing_value3)).setText(Integer.toString(todo.getReal3dealprice()));
                        ((TextView) apt_info.findViewById(R.id.dealing_date3)).setText(Integer.toString(todo.getReal3year()) + "." + Integer.toString(todo.getReal3month()));


                        linearLayoutInfos.addView(apt_info, 0);
                    }
                }

                // 값이 비어있지 않을 경우에만 정수로 파싱
                if (!yhighestPrice.isEmpty()) {
                    yhighestPrice_v = Integer.parseInt(yhighestPrice);
                }
                else{
                    yhighestPrice_v=9999999;
                }
                if (!ylowestPrice.isEmpty()) {
                    ylowestPrice_v = Integer.parseInt(ylowestPrice);
                }
                else{
                    ylowestPrice_v=0;
                }
                if (!dhighestPrice.isEmpty()) {
                    dhighestPrice_v = Integer.parseInt(dhighestPrice);
                }else{
                    dhighestPrice_v=9999999;
                }
                if (!dlowestPrice.isEmpty()) {
                    dlowestPrice_v = Integer.parseInt(dlowestPrice);
                }
                else{
                    dlowestPrice_v=0;
                }
            }
        });
    }

    public void StartMainAlertDialog() {
        // Dialog Message 설정
        // AlertDialog 정의
        AlertDialog.Builder MainAlertDialog = new AlertDialog.Builder(MainAptActivity.this);
        MainAlertDialog.setTitle("사용법"); // Title 설정

        // ImageView 생성 및 이미지 리소스 설정
        ImageView imageView = new ImageView(MainAptActivity.this);
        imageView.setImageResource(R.drawable.alert1); // 여기서 "your_image_resource"는 이미지 리소스의 이름입니다.

        // 이미지뷰를 다이얼로그에 추가
        MainAlertDialog.setView(imageView);

        // positive 버튼 설정
        MainAlertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            // Positive Button에 대한 클릭 이벤트 처리를 구현
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "Close the dialog");
                dialog.dismiss(); // dialog 닫기
            }
        });

        MainAlertDialog.setNeutralButton("오늘 그만 보기", new DialogInterface.OnClickListener() {
            // Neutral Button에 대한 클릭 이벤트 처리를 구현
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor SPreferencesEditor = SPreferences1.edit();
                SPreferencesEditor.putString(NameSPreferences1, strSDFormatDay1); // 오늘 '일(day)' 저장
                SPreferencesEditor.commit(); // important to save the preference
                Log.d(TAG, "Close for a day");
                dialog.dismiss(); // dialog 닫기
            }
        });

        // AlertDialog 화면 출력
        MainAlertDialog.show();
    }

    private void initSpinners() {
        // Spinner에 데이터를 적용하기 위한 ArrayAdapter 초기화
        regionSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.region_array));
        regionSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(regionSpinnerAdapter);

        // regionSpinner의 선택 이벤트 처리
        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // regionSpinner의 선택이 변경될 때 호출되는 메서드
                // 선택된 지역에 따라 sigunguSpinner의 데이터를 동적으로 변경
                String selectedRegion = regionSpinner.getSelectedItem().toString();
                updateSigunguSpinner(selectedRegion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateSigunguSpinner(String selectedRegion) {
        // 선택된 지역에 따라 sigunguSpinner의 데이터를 동적으로 변경
        switch (selectedRegion) {
            case "서울시":
                sigunguSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.seoul_array));
                break;
            case "강원도":
                sigunguSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.kangwon_array));
                break;
            // 다른 지역에 따른 case문 추가
            default:
                sigunguSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{});
        }
        sigunguSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sigunguSpinner.setAdapter(sigunguSpinnerAdapter);
    }

    private void readDataFromCSV() {
        // CSV 파일을 읽어서 dataList에 저장
        InputStream inputStream = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                dataList.add(line);
            }
        } catch (IOException e) {
            Log.e("MainAptActivity", "Error reading data from CSV file:" + e.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void filterAndDisplayData(String region, String sigungu, String aptkind ,int hy ,int ly, int hd, int ld) {
        // 필터링 조건에 맞는 데이터를 찾아서 resultLayout에 출력
        resultLayout.removeAllViews();

        for (String data : dataList) {
            String[] values = data.split(",");
            String leaseRateStr;
            int maxSellingPrice;
            String SmaxSellingPrice;
            int minSellingPrice;
            String SminSellingPrice;
            int maxLeasePrice;
            int minLeasePrice;
            String SmaxLeasePrice;
            String SminLeasePrice;

            // 데이터 파싱

            String kind = values[3];
            String name = values[4];
            String city = values[5];
            String sigunguFilter = values[6];


            if (values[8] != null&& values[8].matches("-?\\d+(\\.\\d+)?")) {
                maxSellingPrice = Integer.parseInt(values[8]);
                SmaxSellingPrice=values[8];
            } else {
                maxSellingPrice = 0;
                SmaxSellingPrice = "정보없음";
            }
            if (values[9] != null&& values[8].matches("-?\\d+(\\.\\d+)?")) {
                minSellingPrice = Integer.parseInt(values[9]);
                SminSellingPrice=values[9];
            } else {
                minSellingPrice = 0;
                SminSellingPrice="정보없음";
            }

            if (values[10] != null&& values[10].matches("-?\\d+(\\.\\d+)?")) {
                maxLeasePrice = Integer.parseInt(values[10]);
                SmaxLeasePrice=values[10];
            } else {
                maxLeasePrice = 0;
                SmaxLeasePrice = "정보없음";
            }
            if (values[11] != null&& values[11].matches("-?\\d+(\\.\\d+)?")) {
                minLeasePrice = Integer.parseInt(values[11]);
                SminLeasePrice=values[11];
            } else {
                minLeasePrice = 0;
                SminLeasePrice="정보없음";
            }

            if (values[12] != null && values[12].matches("-?\\d+(\\.\\d+)?")) {
                double leaseRate = Double.parseDouble(values[12]);
                leaseRateStr = String.format("%.1f", leaseRate*100);
            } else {
                leaseRateStr = "0";
            }

            String[] realEstate1 = new String[3]; // 실거래가
            String[] realEstate2 = new String[3];
            String[] realEstate3 = new String[3];
            for (int i = 0; i < 3; i++) {
                realEstate1[i] = values[13 + i];
                realEstate2[i] = values[16 + i];
                realEstate3[i] = values[19 + i];
            }

            // 필터링 조건에 맞는 데이터 출력
            if (city.equals(region) && sigunguFilter.equals(sigungu)) {

                if (aptkind.equals(kind)) {

                    if((hy>=maxSellingPrice) && (ly<=minSellingPrice)&&(hd>=maxLeasePrice) && (ld<=minLeasePrice)) {
                        addapt(name, kind, leaseRateStr, SmaxSellingPrice, SminSellingPrice, SmaxLeasePrice,SminLeasePrice,realEstate1, realEstate2, realEstate3);
                    }
                }
            }




        }

    }
    public void addapt(String name, String kind, String leaseRate, String maxSellingPrice, String minSellingPrice, String maxLeasePrice, String minLeasePrice, String[] realEstate1, String[] realEstate2, String[] realEstate3) {
        ViewGroup apt_info = (ViewGroup) inflater.inflate(R.layout.activity_apt_info, linearLayoutInfos, false);
        ((TextView) apt_info.findViewById(R.id.textView_name)).setText(name + "(" + kind + ")");
        ((TextView) apt_info.findViewById(R.id.textView_rate)).setText(leaseRate + "%");
        ((TextView) apt_info.findViewById(R.id.dealH_value)).setText(maxSellingPrice);
        ((TextView) apt_info.findViewById(R.id.dealL_value)).setText(minSellingPrice);
        ((TextView) apt_info.findViewById(R.id.yearH_value)).setText(maxLeasePrice);
        ((TextView) apt_info.findViewById(R.id.yearL_value)).setText(minLeasePrice);

        ((TextView) apt_info.findViewById(R.id.dealing_value1)).setText(realEstate1[2]);
        ((TextView) apt_info.findViewById(R.id.dealing_date1)).setText(realEstate1[0]+"."+realEstate1[1]);

        ((TextView) apt_info.findViewById(R.id.dealing_value2)).setText(realEstate2[2]);
        ((TextView) apt_info.findViewById(R.id.dealing_date2)).setText(realEstate2[0]+"."+realEstate2[1]);


        ((TextView) apt_info.findViewById(R.id.dealing_value3)).setText(realEstate3[2]);
        ((TextView) apt_info.findViewById(R.id.dealing_date3)).setText(realEstate3[0]+"."+realEstate3[1]);


        linearLayoutInfos.addView(apt_info, 0);

    }


}
