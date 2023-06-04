package com.example.myapplication;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adpter.HomeAdapter;
import com.example.myapplication.item.HomeListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class ApiActivity extends AppCompatActivity {

    private static final int NETWORK_SUCCESS = 1;
    private static final int NETWORK_FAIL = 2;
    private Button getApi, startDate, endDate, mainPopup, subPopup, apiNext, apiBefore;
    private TextView firstPage, lastPage , currentPage, NoData;
    private RecyclerView apiRecyclerView;
    private PopupMenu subPopupMenu;
    private Calendar calendar;
    private String bigChoice = "";
    private String smallChoice = "";
    private String choice1 = "";
    private String choice2 = "";
    private String choice3 = "";
    private int first = 1;
    private int last = 20;
    private int current = 1;
    private String sCurrent = "1";
    private String start = null;
    private String end = null;

    private final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case NETWORK_SUCCESS:
                    String response = (String) msg.obj;
                    try {

                        ArrayList<HomeListItem> homeListItemArrayList = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(1);

                        String s1 = jsonObject.toString();
                        JSONObject jsonObject1 = new JSONObject(s1);

                        if(jsonObject1.has("dsList")){
                            NoData.setVisibility(View.GONE);
                            apiRecyclerView.setVisibility(View.VISIBLE);
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("dsList");
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                HomeListItem homeListItem = new HomeListItem();
                                JSONObject js = new JSONObject(jsonArray1.get(i).toString());
                                String ur = js.getString("LINK_URL");
                                String seUl = ur.replaceFirst("^http", "https");
                                String sPage = js.getString("PAGE");
                                String sRnum = js.getString("RNUM");
                                String sAllCnt = js.getString("ALL_CNT");
                                String sBbsSn = js.getString("BBS_SN");
                                String sInqCnt = js.getString("INQ_CNT");
                                homeListItem.setLinkUrl(seUl);
                                homeListItem.setBbsWouDttm(js.getString("BBS_WOU_DTTM"));
                                homeListItem.setBbsTl(js.getString("BBS_TL"));
                                homeListItem.setAisTpCdNm(js.getString("AIS_TP_CD_NM"));
                                homeListItem.setCcrCnntSysDsCd(js.getString("CCR_CNNT_SYS_DS_CD"));
                                homeListItem.setDepNm(js.getString("DEP_NM"));
                                homeListItem.setPage(Integer.parseInt(sPage));
                                homeListItem.setrNum(Integer.parseInt(sRnum));
                                homeListItem.setAllCnt(Integer.parseInt(sAllCnt));
                                homeListItem.setBbsSn(Long.parseLong(sBbsSn));
                                homeListItem.setInqCnt(Integer.parseInt(sInqCnt));
                                homeListItemArrayList.add(homeListItem);

                            }
                            if (homeListItemArrayList.size() > 0){
                                last = homeListItemArrayList.get(0).getAllCnt() / 10 + 1;
                                lastPage.setText(String.valueOf(last));

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                                apiRecyclerView.setLayoutManager(linearLayoutManager);

                                HomeAdapter homeAdapter = new HomeAdapter(homeListItemArrayList);
                                apiRecyclerView.setAdapter(homeAdapter);
                            }

                        } else{
                            NoData.setVisibility(View.VISIBLE);
                            apiRecyclerView.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                    break;
                case NETWORK_FAIL:
                    Exception e = (Exception) msg.obj;
                    Log.e("ApiActivity", "Network Error", e);
                    e.printStackTrace();
                    break;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        apiRecyclerView = findViewById(R.id.apiRecyclerView);
        getApi = findViewById(R.id.getApi);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        mainPopup = findViewById(R.id.mainPopup);
        subPopup = findViewById(R.id.subPopup);
        apiBefore = findViewById(R.id.apiBefore);
        apiNext = findViewById(R.id.apiNext);
        firstPage = findViewById(R.id.firstPage);
        currentPage = findViewById(R.id.currentPage);
        lastPage = findViewById(R.id.lastPage);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        NoData = findViewById(R.id.NoData);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(startDate);
                start = startDate.getText().toString();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(endDate);
                end = endDate.getText().toString();
            }
        });

        firstPage.setText(String.valueOf(first));
        lastPage.setText(String.valueOf(last));
        currentPage.setText(String.valueOf(current));

        calendar = Calendar.getInstance();

        apiNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current < last){
                    current++;
                    currentPage.setText(String.valueOf(current));
                    sCurrent = String.valueOf(current);
                    requestApiData();
                }
            }
        });

        apiBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current > first){
                    current--;
                    currentPage.setText(String.valueOf(current));
                    sCurrent = String.valueOf(current);
                    requestApiData();
                }
            }
        });

        mainPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ApiActivity.this, v);
                getMenuInflater().inflate(R.menu.api_main_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String selectedMainOption = item.getTitle().toString();
                        mainPopup.setText(selectedMainOption);
                        if(item.getItemId() == R.id.all){
                            System.out.println("전체");
                            subNullPopup();
                            subPopupMenu.show();
                            bigChoice = "";
                        } else if (item.getItemId() == R.id.land) {
                            System.out.println("토지");
                            subLandPopup();
                            subPopupMenu.show();
                            bigChoice = "01";
                        } else if (item.getItemId() == R.id.condominium) {
                            System.out.println("분양주택");
                            subNullPopup();
                            subPopupMenu.show();
                            bigChoice = "05";
                        } else if (item.getItemId() == R.id.honeymoon) {
                            System.out.println("신혼희망타운");
                            subNullPopup();
                            subPopupMenu.show();
                            bigChoice = "39";
                        } else if (item.getItemId() == R.id.lease) {
                            System.out.println("임대주택");
                            subLeasePopup();
                            subPopupMenu.show();
                            bigChoice = "06";
                        } else if(item.getItemId() == R.id.charter){
                            System.out.println("매입임대/전세임대");
                            subCharterPopup();
                            subPopupMenu.show();
                            bigChoice = "13";
                        } else if (item.getItemId() == R.id.store) {
                            System.out.println("상가");
                            subStorePopup();
                            subPopupMenu.show();
                            bigChoice = "22";
                        } else if (item.getItemId() == R.id.other) {
                            System.out.println("기타");
                            subNullPopup();
                            subPopupMenu.show();
                            bigChoice = "99";
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        subPopupMenu = new PopupMenu(ApiActivity.this, subPopup);
        subPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String selectedSubOption = item.getTitle().toString();
                subPopup.setText(selectedSubOption);
                switch (selectedSubOption) {
                    case "전체":
                        setChoice("", "", "", "");
                        break;
                    case "주택용지":
                        setChoice("02", "", "", "");
                        break;
                    case "상가용지":
                        setChoice("03", "", "", "");
                        break;
                    case "산업시설용지":
                        setChoice("04", "", "", "");
                        break;
                    case "기타용지":
                        setChoice("28", "", "", "");
                        break;
                    case "국민임대":
                        setChoice("07", "", "", "");
                        break;
                    case "공공임대":
                        setChoice("08", "", "", "");
                        break;
                    case "영구임대":
                        setChoice("09", "", "", "");
                        break;
                    case "행복주택":
                        setChoice("10", "", "", "");
                        break;
                    case "장기전세":
                        setChoice("11", "", "", "");
                        break;
                    case "신축다세대":
                        setChoice("12", "", "", "");
                        break;
                    case "매입임대":
                        setChoice("", "", "26", "");
                        break;
                    case "전세임대":
                        setChoice("", "","", "17");
                        break;
                    case "집주인임대":
                        setChoice("", "36", "", "");
                        break;
                    case "분양 (구)임대상가(입찰)":
                        setChoice("", "22","","");
                        break;
                    case "임대상가(입찰)":
                        setChoice("43","","","");
                        break;
                    case "임대상가(공모.심사)":
                        setChoice("38","","","");
                        break;
                    case "임대상가(추첨)":
                        setChoice("24", "", "", "");
                        break;
                    default:
                        // 기본적으로 수행할 동작을 설정합니다.
                        break;
                }
                return true;
            }
        });

        subPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subPopupMenu.show();
            }
        });

        getApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current = 1;
                currentPage.setText(String.valueOf(current));
                sCurrent = String.valueOf(current);
                requestApiData();
            }
        });

    }

    private void subLandPopup(){
        subPopupMenu.getMenu().clear();
        getMenuInflater().inflate(R.menu.api_sub_land_menu, subPopupMenu.getMenu());
    }
    private void subLeasePopup(){
        subPopupMenu.getMenu().clear();
        getMenuInflater().inflate(R.menu.api_sub_lease_menu, subPopupMenu.getMenu());
    }
    private void subCharterPopup(){
        subPopupMenu.getMenu().clear();
        getMenuInflater().inflate(R.menu.api_sub_charter_menu, subPopupMenu.getMenu());
    }
    private void subStorePopup(){
        subPopupMenu.getMenu().clear();
        getMenuInflater().inflate(R.menu.api_sub_store_menu, subPopupMenu.getMenu());
    }
    private void subNullPopup(){
        subPopupMenu.getMenu().clear();
        getMenuInflater().inflate(R.menu.api_sub_null_menu, subPopupMenu.getMenu());
    }

    private void showDatePicker(final Button button){
        DatePickerDialog datePickerDialog = new DatePickerDialog(ApiActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                        button.setText(date);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
    private void setChoice(String smallChoice, String choice1, String choice2, String choice3){
        this.smallChoice = smallChoice;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
    }

    private void requestApiData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552555/lhNoticeInfo1/getNoticeInfo1"); /*URL*/
                urlBuilder.append("?" + Uri.encode("serviceKey","UTF-8") + "=rGX13KgLV52QZCorESs2W0hSJEM1LfJHl9mcwdz2qENKOlmQ%2FO%2F4q1Y5%2FpONEsqfv1zCd3GxBmkx7udFGC10CQ%3D%3D"); /*Service Key*/
                urlBuilder.append("&" + Uri.encode("PG_SZ","UTF-8") + "=" + Uri.encode("10", "UTF-8")); /*한 페이지 결과 수*/
                urlBuilder.append("&" + Uri.encode("PAGE","UTF-8") + "=" + Uri.encode(sCurrent, "UTF-8")); /*페이지번호*/
                urlBuilder.append("&" + Uri.encode("SCH_ST_DT","UTF-8") + "=" + Uri.encode(start, "UTF-8")); /*기간검색-시작일*/
                urlBuilder.append("&" + Uri.encode("SCH_ED_DT","UTF-8") + "=" + Uri.encode(end, "UTF-8")); /*기간검색-종료일*/
                urlBuilder.append("&" + Uri.encode("BBS_TL","UTF-8") + "=" + Uri.encode("", "UTF-8")); /*검색어-제목*/
                urlBuilder.append("&" + Uri.encode("BBS_DTL_CTS","UTF-8") + "=" + Uri.encode("", "UTF-8")); /*검색어-내용*/
                urlBuilder.append("&" + Uri.encode("UPP_AIS_TP_CD","UTF-8") + "=" + Uri.encode(bigChoice, "UTF-8")); /*상위유형코드 토지, 분양주택, 신혼 희망 타운, 임대주택, 매입임대, 전세임대, 상가 기타*/
                urlBuilder.append("&" + Uri.encode("AIS_TP_CD","UTF-8") + "=" + Uri.encode(smallChoice, "UTF-8")); /*유형코드 주택용지,국민임대주택, 집주인임대*/
                urlBuilder.append("&" + Uri.encode("AIS_TP_CD_INT","UTF-8") + "=" + Uri.encode(choice1, "UTF-8"));  /*유형코드 추가 집주인 임대 조회용*/
                urlBuilder.append("&" + Uri.encode("AIS_TP_CD_INT2","UTF-8") + "=" + Uri.encode(choice2, "UTF-8")); /*유형코드-추가2 매입임대 조회용*/
                urlBuilder.append("&" + Uri.encode("AIS_TP_CD_INT3","UTF-8") + "=" + Uri.encode(choice3, "UTF-8")); /*유형코드-추가3 전세임대 조회용*/

                try{
                    InputStream is = getResources().openRawResource(R.raw.server1);
                    CertificateFactory cf = CertificateFactory.getInstance("X.509");
                    X509Certificate serverCert = (X509Certificate)cf.generateCertificate(is);

                    KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                    trustStore.load(null, null);
                    trustStore.setCertificateEntry("myserver", serverCert);

                    TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    tmf.init(trustStore);
                    X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];

                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    sslContext.init(null, new TrustManager[]{defaultTrustManager}, null);

                    URL url = new URL(urlBuilder.toString());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-type", "application/json");

                    BufferedReader rd = null;
                    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300){
                        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    } else {
                        rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    }

                    StringBuilder sb = new StringBuilder();
                    String line;
                    while((line = rd.readLine()) != null){
                        sb.append(line);
                    }
                    rd.close();
                    conn.disconnect();

                    int API_REQUEST_COMPLETED = 1;
                    Message message = mHandler.obtainMessage(API_REQUEST_COMPLETED, sb.toString());
                    mHandler.sendMessage(message);

                }catch (IOException | CertificateException | KeyStoreException |
                        NoSuchAlgorithmException | KeyManagementException ex){
                    throw new RuntimeException(ex);
                }
            }
        }).start();
    }
}