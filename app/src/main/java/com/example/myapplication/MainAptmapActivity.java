package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.MarkerIcons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainAptmapActivity extends FragmentActivity implements OnMapReadyCallback { //extends AppCompatActivity
    private static final String TAG = "MainAptmapActivity";
    private ArrayAdapter<String> regionSpinnerAdapter;
    private ArrayAdapter<String> sigunguSpinnerAdapter;
    private Spinner regionSpinner;
    private Spinner sigunguSpinner;

    private EditText searchname;

    private List<Todo> allTodos = new ArrayList<>();

    private SharedPreferences SPreferences2;
    private final String NameSPreferences2 = "Day";
    private String strSDFormatDay2 = "0";

    private String[][] office = { //각 지역의 시군구청 좌표. 춘천은 강원대.
            {"전체", "37.566295", "126.977945"},//서울 시청
            {"강남구", "37.517288", "127.047482"},
            {"강동구", "37.530112", "127.123793"},
            {"강북구", "37.639786", "127.025620"},
            {"강서구", "37.550968", "126.849685"},
            {"관악구", "37.478184", "126.951468"},
            {"광진구", "37.538654", "127.082379"},
            {"구로구", "37.4955", "126.8876"},
            {"금천구", "37.4602", "126.9005"},
            {"노원구", "37.6542", "127.0568"},
            {"도봉구", "37.668775", "127.047167"},
            {"동대문구", "37.5835", "127.0506"},
            {"동작구", "37.5129", "126.9395"},
            {"마포구", "37.566378", "126.901457"},
            {"서대문구", "37.579239", "126.936868"},
            {"서초구", "37.4763", "127.0375"},
            {"성동구", "37.563453", "127.036815"},
            {"성북구", "37.5890", "127.0165"},
            {"송파구", "37.5041", "127.1147"},
            {"양천구", "37.5169", "126.8666"},
            {"영등포구", "37.5249", "126.8953"},
            {"용산구", "37.532560", "126.990505"},
            {"은평구", "37.602760", "126.929120"},
            {"종로구", "37.5728", "126.9795"},
            {"중구", "37.5636", "126.9975"},
            {"중랑구", "37.606382", "127.092611"},
            //강원도. 강원도 전체는 춘천 기준
            {"춘천시", "37.869972", "127.743389"},
            {"원주시", "37.3442", "127.9201"},
            {"강릉시", "37.7520", "128.8750"},
            {"동해시", "37.5225", "129.1135"},
            {"태백시", "37.1645", "128.9877"},
            {"속초시", "38.2070", "128.5915"},
            {"삼척시", "37.4454", "129.1682"},
            {"홍천군", "37.6908", "127.8848"},
            {"횡성군", "37.4896", "127.9853"},
            {"영월군", "37.1846", "128.4676"},
            {"평창군", "37.3705", "128.3943"},
            {"정선군", "37.3815", "128.6595"},
            {"철원군", "38.1463", "127.3019"},
            {"화천군", "38.1065", "127.7083"},
            {"양구군", "38.1058", "127.9855"},
            {"인제군", "38.0676", "128.1668"},
            {"고성군", "38.3806", "128.4679"},
            {"양양군", "38.0803", "128.6195"}};

    private MapFragment mapFragment;
    private NaverMap naverMap;

    private int dataNum; //데이터 개수
    private Marker[] markers;

    private InfoWindow[] infoWindows;

    //지도 초기 위치와 줌 설정(강원대)
    private NaverMapOptions options = new NaverMapOptions()
            .camera(new CameraPosition(new LatLng(37.869972, 127.743389), 15));
    private FragmentManager fm;

    private int markerCount=0; //마커 개수

    //    private String Apturl = "https://new.land.naver.com/complexes/";
    private String Apturl = "https://m.land.naver.com/map/";
    //    private String Houurl = "https://new.land.naver.com/houses?ms=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aptmap);
        final AppDatabase db= Room.databaseBuilder(this, AppDatabase.class, "kang.db")
                .createFromAsset("database/kang.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        long CurrentTime = System.currentTimeMillis(); // 현재 시간을 msec 단위로 얻음
        Date TodayDate = new Date(CurrentTime); // 현재 시간 Date 변수에 저장
        SimpleDateFormat SDFormat = new SimpleDateFormat("dd");
        strSDFormatDay2 = SDFormat.format(TodayDate); // 'dd' 형태로 포맷 변경

        // SharedPreferences 획득
        SPreferences2 = PreferenceManager.getDefaultSharedPreferences(this);
        String strSPreferencesDay = SPreferences2.getString(NameSPreferences2, "0");

        // 공지사항 알림창 띄움
        if((Integer.parseInt(strSDFormatDay2) - Integer.parseInt(strSPreferencesDay)) != 0)
            StartMainAlertDialog();

        readDB(db); //DB에서 데이터 받아오고 마커와 정보창 배열 설정.
        if (mapFragment == null) {
            makeMap(); //지도 생성
        }
        Button searchButton = findViewById(R.id.buttonSearch);
        // Initialize the spinners and adapters
        regionSpinner = findViewById(R.id.region_array);
        sigunguSpinner = findViewById(R.id.sigungu_array);
        searchname = findViewById(R.id.namesearch);
        initSpinners();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //검색 클릭 시
                String selectedRegion = regionSpinner.getSelectedItem().toString();
                String selectedSigungu = sigunguSpinner.getSelectedItem().toString();
                String name=searchname.getText().toString();
                makeMarker(selectedRegion,selectedSigungu,name, naverMap); //지역 조건에 맞는 마커 생성.
            }
        });
    }
    public void StartMainAlertDialog() {
        // Dialog Message 설정
        // AlertDialog 정의
        AlertDialog.Builder MainAlertDialog = new AlertDialog.Builder(MainAptmapActivity.this);
        MainAlertDialog.setTitle("사용법"); // Title 설정

        // ImageView 생성 및 이미지 리소스 설정
        ImageView imageView = new ImageView(MainAptmapActivity.this);
        imageView.setImageResource(R.drawable.alert2); // 여기서 "your_image_resource"는 이미지 리소스의 이름입니다.

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
                SharedPreferences.Editor SPreferencesEditor = SPreferences2.edit();
                SPreferencesEditor.putString(NameSPreferences2, strSDFormatDay2); // 오늘 '일(day)' 저장
                SPreferencesEditor.commit(); // important to save the preference
                Log.d(TAG, "Close for a day");
                dialog.dismiss(); // dialog 닫기
            }
        });

        // AlertDialog 화면 출력
        MainAlertDialog.show();
    }

    private void makeMap(){ //지도 생성
        fm = getSupportFragmentManager();
        mapFragment = (MapFragment) fm.findFragmentById(R.id.map);

        mapFragment = MapFragment.newInstance(options);
        fm.beginTransaction().add(R.id.map, mapFragment).commit();

        mapFragment.getMapAsync(this);
    }

    //지역 선택 시 그 지역으로 이동. 춘천은 강원대, 나머진 시청.
    private void moveCamera(NaverMap naverMap, String region, String sigungu){
        if(region.equals("강원도") && sigungu.equals("전체")){
            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.869972, 127.743389));
            naverMap.moveCamera(cameraUpdate);
            return;
        }
        for(String[] of : office){
            if(of[0].equals(sigungu)){
                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(Double.parseDouble(of[1]), Double.parseDouble(of[2])));
                naverMap.moveCamera(cameraUpdate);
                return;
            }
        }
    }


    private void readDB(AppDatabase db){ //DB에서 모든 데이터를 받아 allTodos에 저장한다.

        allTodos = db.todoDao().getAll();
        dataNum=allTodos.size(); //데이터 개수 설정
        markers=new Marker[dataNum]; //데이터 개수로 마커 배열 설정
        infoWindows=new InfoWindow[dataNum]; //데이터 개수로 정보창 배열 설정

    }


    private void chColor(Marker marker, String leaseRate){ //전세가율에 따라 마커 색깔 변경
        if(leaseRate.equals("정보없음") || leaseRate.equals("0")){
            //전세가율 정보가 없으면 검정
        }
        else{
            Double r = Double.parseDouble(leaseRate);
            if(r<=45){//50 이하면 초록
                marker.setIconTintColor(Color.GREEN);
            }
            else if(r<=60){//75 이하면 노랑
                marker.setIconTintColor(Color.BLUE);
            }
            else {//그 초과면 레드
                marker.setIconTintColor(Color.RED);
            }
        }

    }

    //위도와 경도를 가지고 마커 정보창에 표시할 매물 정보 url을 만든다.
    private String makeUrl(String kind, double lati, double logi, int colnum){
        String url;
        if(kind.equals("아파트")){
            url = Apturl+lati+":"+logi+":17/APT/A1:B1#mapList";
            return url;
        }
        else{
            url=Apturl+lati+":"+logi+":17/JWJT:DDDGG:SGJT:HOJT/A1:B1#mapList";
            return url;
        }

    }

    private void createMarker(Todo todo, int num, String name){//받아온 todo값과 이름에 따라 마커와 정보창을 생성.
        String leaseRateStr;
        String SmaxSellingPrice;
        String SminSellingPrice;
        String SmaxLeasePrice;
        String SminLeasePrice;
        String kind = todo.getType();
        int colnum = todo.getColnum();
        double lati =todo.getLatitude(); //위도
        double logi = todo.getLongitude(); //경도
        if(todo.getMaxdealprice()==0){
            SmaxSellingPrice="정보없음";
        }
        else {
            SmaxSellingPrice = Integer.toString(todo.getMaxdealprice());
        }

        if(todo.getMindealprice()==0){
            SminSellingPrice = "정보없음";
        }
        else {
            SminSellingPrice = Integer.toString(todo.getMindealprice());
        }

        if(todo.getMaxlprice()==0){
            SmaxLeasePrice = "정보없음";
        }
        else {
            SmaxLeasePrice = Integer.toString(todo.getMaxlprice());
        }

        if(todo.getMinlprice()==0){
            SminLeasePrice = "정보없음";
        }
        else {
            SminLeasePrice =Integer.toString(todo.getMinlprice());
        }

        if(todo.getLrate()==0){
            leaseRateStr = "정보없음";
        }
        else {
            leaseRateStr = Double.toString(Math.round(todo.getLrate()*100 * 10.0) / 10.0);
        }
        //마커와 정보창 생성.
        infoWindows[num] = new InfoWindow();
        markers[num] = new Marker();
        markers[num].setPosition(new LatLng(lati, logi));
        markers[num].setCaptionText(name);
        markers[num].setCaptionAligns(Align.Center);
        markers[num].setIcon(MarkerIcons.BLACK);
        chColor(markers[num], leaseRateStr);
        markers[num].setMap(naverMap);
        infoWindows[num].setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {//정보창 내용 추가.
                return "매물 이름 : " + name + "("+kind+")\n전세가율 : " + leaseRateStr + "%\n매매 가격 : " + SminSellingPrice + "~" + SmaxSellingPrice
                        + "\n전세 가격 : " + SminLeasePrice + "~" + SmaxLeasePrice+"\n정보창 클릭 시 네이버 부동산으로 이동";
            }
        });
        int finalNum = num;
        markers[num].setOnClickListener(overlay -> {//마커 클릭시 정보창 표시/감춤
            if (markers[finalNum].getInfoWindow() == null) {
                infoWindows[finalNum].open(markers[finalNum]);
            } else infoWindows[finalNum].close();


            return true;
        });
        infoWindows[num].setOnClickListener(overlay -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(makeUrl(kind,lati,logi,colnum)));
            startActivity(intent);
            return true;
        });
    }

    private void makeMarker(String region, String sigungu, String Aptname, NaverMap naverMap) {
//지역, 시군구, 이름을 받아와 조건을 비교해 마커와 정보창을 만든다.
        int num=0;
        moveCamera(naverMap,region, sigungu);//선택한 지역으로 이동
//마커 초기화
        for (int i=0; i<markerCount; i++){
            if(markers[i] !=null){
                markers[i].setMap(null);
            }
        }
        markerCount=0;
        for (Todo todo : allTodos) {
            Log.d("MyApp", "Todos: " + todo);
            String name = todo.getName();
            String city = todo.getSi();
            String sigunguFilter = todo.getGu();
            // 필터링 조건에 맞는 데이터 출력
            if(Aptname.isEmpty()){ //이름 없는 경우
                if(city.equals(region) && sigungu.equals("전체")){ //전체를 선택한 경우
                    createMarker(todo,num, name); //마커와 정보창 생성.
                    num++;
                }
                else if (city.equals(region) && sigunguFilter.equals(sigungu)) {//지역과 시군구를 선택한 경우
                    createMarker(todo,num, name);
                    num++;
                }
                else{

                }
            }
            else { //이름 포함 검색
                if(name.contains(Aptname)){
                    if(city.equals(region) && sigungu.equals("전체")){ //전체를 선택한 경우
                        createMarker(todo,num, name);
                        num++;
                    }
                    else if (city.equals(region) && sigunguFilter.equals(sigungu)) {//지역과 시군구를 선택한 경우
                        createMarker(todo,num, name);
                        num++;
                    }
                    else{

                    }
                }
            }
        }
        markerCount=num;
    }

    private void initSpinners() {
        // Spinner에 데이터를 적용하기 위한 ArrayAdapter 초기화
        regionSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.region_array));
        regionSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(regionSpinnerAdapter);

        // sigunguSpinnerAdapter는 초기에 빈 배열로 설정
        sigunguSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{});
        sigunguSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sigunguSpinner.setAdapter(sigunguSpinnerAdapter);

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

    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap=naverMap;

    }
}

