package com.example.ekycdemo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.caverock.androidsvg.SVGImageView;
import com.example.ekycdemo.R;
import com.example.ekycdemo.controllers.ApiService;
import com.example.ekycdemo.controllers.CustomAdapter;
import com.example.ekycdemo.controllers.SharedPreferencesManager;
import com.example.ekycdemo.databinding.ActivityPersonalDetailsBinding;
import com.example.ekycdemo.models.CountryList;
import com.example.ekycdemo.models.IntendOfAccount;
import com.example.ekycdemo.models.KycData;
import com.example.ekycdemo.models.PostalCodeModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonalDetailsActivity extends AppCompatActivity {
    ActivityPersonalDetailsBinding binding;
    ArrayList<String> arrayList;
    Dialog dialog;
    CountryList countries;
    String cardName = "";
    boolean isFromKycConfirmationPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        isFromKycConfirmationPage = getIntent().getBooleanExtra("isFromKycConfirmation", false);

        Bundle bundle = getIntent().getExtras();
        String venName = bundle.getParcelable("apiData1");
        Gson gson = new Gson();
        System.out.println("hagsudgsadhdksah" + gson.toJson(venName));
        arrayList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://rpsremit.truestreamz.com/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<IntendOfAccount> call = apiService.getIntendOfAccountList();
        call.enqueue(new Callback<IntendOfAccount>() {
            @Override
            public void onResponse(Call<IntendOfAccount> call, Response<IntendOfAccount> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().data.isEmpty()) {
                    int len = response.body().data.get(0).data.size();
                    for (int i = 0; i < len; i++) {
                        arrayList.add(response.body().data.get(0).data.get(i).nameEn);
                    }

                }
            }

            @Override
            public void onFailure(Call<IntendOfAccount> call, Throwable t) {

            }
        });
        Call<CountryList> countryListCall = apiService.getCountriesList("1");
        countryListCall.enqueue(new Callback<CountryList>() {
            @Override
            public void onResponse(Call<CountryList> call, Response<CountryList> response) {

                if (response.isSuccessful()) {
                    countries = response.body();
                }
            }

            @Override
            public void onFailure(Call<CountryList> call, Throwable t) {
            }
        });

        filterViewAccordingToCard();
        setOnClickListeners();

    }

    private void setOnClickListeners() {

        binding.titleEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_GO) {
                    binding.editTextFirstName.requestFocus();
                    return true;
                }
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    return true;
                }
                return false;
            }
        });

        //postal code auto fill
        binding.searchIcon.setOnClickListener(v -> {
            String postalCode = binding.editTextPostalCode.getText().toString();
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://rpsremit.truestreamz.com/").addConverterFactory(GsonConverterFactory.create()).build();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<PostalCodeModel> call = apiService.getPostalCode(postalCode);
            call.enqueue(new Callback<PostalCodeModel>() {
                @Override
                public void onResponse(Call<PostalCodeModel> call, Response<PostalCodeModel> response) {

                    if (response.isSuccessful()) {
                        if (response.body().data.isEmpty()) {
                            Toast.makeText(PersonalDetailsActivity.this, "No postal code found for given postal code.", Toast.LENGTH_SHORT).show();
                        } else {
                            binding.postalCodeData.setVisibility(View.VISIBLE);
                            binding.editTextPrefecture.setText(response.body().data.get(0).prefecture);
                            disableEditText(binding.editTextPrefecture);
                            binding.editTextCity.setText(response.body().data.get(0).city);
                            disableEditText(binding.editTextCity);
                            binding.editTextStreet.setText(response.body().data.get(0).street);
                            disableEditText(binding.editTextStreet);
                        }
                        if (binding.editTextPostalCode.hasFocus()) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(binding.editTextPostalCode.getWindowToken(), 0);
                        }
                    }
                }

                @Override
                public void onFailure(Call<PostalCodeModel> call, Throwable t) {
                    if (binding.editTextPostalCode.hasFocus()) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(binding.editTextPostalCode.getWindowToken(), 0);
                    }
                    Toast.makeText(PersonalDetailsActivity.this, "Error getting postal code", Toast.LENGTH_SHORT).show();
                }
            });
        });
        //expiry date
        binding.editTextDateOfIssue.setOnClickListener(v -> {
            showDatePicker(binding.editTextDateOfIssue,false);

        });
        binding.editTextExpiryDate.setOnClickListener(v -> {
            showDatePicker(binding.editTextExpiryDate,false);

        });
        binding.editTextDateOfBirth.setOnClickListener(v -> {
            showDatePicker(binding.editTextDateOfBirth,true);

        });
        binding.intendOfUse.setOnClickListener(v -> {
            dialog = new Dialog(PersonalDetailsActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_searchable_spinner);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            EditText editText = dialog.findViewById(R.id.edit_text);
            ListView listView = dialog.findViewById(R.id.list_view);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
            listView.setAdapter(arrayAdapter);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    arrayAdapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    binding.intendOfUse.setText(arrayAdapter.getItem(position));
                    dialog.dismiss();
                }
            });
        });

        binding.editTextNationality.setOnClickListener(v -> {
            dialog = new Dialog(PersonalDetailsActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_searchable_spinner);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            EditText editText = dialog.findViewById(R.id.edit_text);
            ListView listView = dialog.findViewById(R.id.list_view);
            CustomAdapter customAdapter = new CustomAdapter(this, countries.countries);
            listView.setAdapter(customAdapter);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    customAdapter.getFilter().filter(s.toString());

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SVGImageView imageView;
                    imageView = (SVGImageView) findViewById(R.id.imageViewIconLeft) ;
                    customAdapter.loadSvgImage(customAdapter.getIconUrl(position),imageView);

                    binding.editTextNationality.setPadding(75,10,0,35);
                    binding.imageViewIconLeft.setVisibility(View.VISIBLE);
                    binding.editTextNationality.setText(parent.getItemAtPosition(position).toString());
                    dialog.dismiss();
                }
            });
        });
        binding.btnNextPersonalDetail.frontImgSaveBtn.setOnClickListener(v -> {


            if (checkAllFields()) {

                KycData data = new KycData();
                data.setTitle(binding.titleEditText.getText().toString());
                data.setFirst_name(binding.editTextFirstName.getText().toString());
                data.setMiddleName(binding.middleName.getText().toString());
                data.setLast_name(binding.editTextLastName.getText().toString());
                data.setDateOfBirth(binding.editTextDateOfBirth.getText().toString());
                data.setNationality(binding.editTextNationality.getText().toString());
                data.setIntended_use_of_account(binding.intendOfUse.getText().toString());
                data.setMobile_number(binding.editTextMobileNumber.getText().toString());
                data.setPhone_number(binding.editTextPhoneNumber.getText().toString());
                data.setGender(binding.editTextGender.getText().toString());
                data.setDob(binding.editTextDateOfBirth.getText().toString());
                data.setCard_expiry_date(binding.editTextExpiryDate.getText().toString());
                data.setPeriod_of_stay(binding.editTextPeriodOfStay.getText().toString());
                data.setCard_issue_date(binding.editTextDateOfIssue.getText().toString());
                data.setPrefecture(binding.editTextPrefecture.getText().toString());
                data.setCity(binding.editTextCity.getText().toString());
                data.setStreet(binding.editTextStreet.getText().toString());
                data.setBuilding_name(binding.editTextBuildingName.getEditText().getText().toString());
                data.setPostal_code(binding.editTextPostalCode.getText().toString());

                Intent intent;
                if (isFromKycConfirmationPage) {
                    intent = new Intent(this, KycConfirmationPage.class);
                } else {
                    intent = new Intent(this, AdditionalInformation.class);
                }
                intent.putExtra("kycData", data);
                startActivity(intent);
            }
        });
        binding.editTextPeriodOfStay.setOnClickListener(v -> {
            showDatePicker(binding.editTextPeriodOfStay,false);
        });
    }

    private void filterViewAccordingToCard() {
        cardName = SharedPreferencesManager.getCardName(PersonalDetailsActivity.this);
        if (cardName.toLowerCase().contains("resident")) {
            cardName += "\nDetails";
        }
        if (cardName.toLowerCase().contains("my")) {
            cardName = "My Card\nNumber";
        }
        if (cardName.toLowerCase().contains("driv")) {
            cardName = "Driving\nLicense";
        }
        binding.titlePersonalDetail.setText(cardName);
        if (cardName.toLowerCase().contains("my")) {
            binding.dateOfIssueTextView.setVisibility(View.GONE);
            binding.dateOfIssueInputFiled.setVisibility(View.GONE);
            binding.periodOfStayTextView.setVisibility(View.GONE);
            binding.periodOfStayInputFiled.setVisibility(View.GONE);
        }
        if (cardName.toLowerCase().contains("driving")) {
            binding.periodOfStayTextView.setVisibility(View.GONE);
            binding.periodOfStayInputFiled.setVisibility(View.GONE);
        }
    }

    private void showDatePicker(TextInputEditText editTextExpiryDate,boolean isDob) {
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(
                PersonalDetailsActivity.this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    editTextExpiryDate.setText(year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    editTextExpiryDate.setError(null);
                    editTextExpiryDate.clearFocus();
                },
                year, month, day);
        if(isDob) {
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 568025136000L);
        }else{
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        }
        datePickerDialog.show();
    }

    private void disableEditText(TextInputEditText editTextPrefecture) {
        editTextPrefecture.setFocusable(false);
        editTextPrefecture.setFocusableInTouchMode(false);
        editTextPrefecture.setClickable(false);
        editTextPrefecture.setCursorVisible(false);
    }

    private boolean checkAllFields() {
        if (binding.titleEditText.getText().toString().trim().length() == 0) {
            binding.titleEditText.requestFocus();
            binding.titleEditText.setError("Tittle is required");
            return false;
        }
        if (binding.editTextFirstName.getText().toString().trim().length() == 0) {
            binding.editTextFirstName.requestFocus();
            binding.editTextFirstName.setError("First name is required");
            return false;
        }
        if (binding.editTextLastName.getText().toString().trim().length() == 0) {
            binding.editTextLastName.requestFocus();
            binding.editTextLastName.setError("Last name is required");
            return false;
        }
        if (binding.editTextDateOfBirth.getText().toString().trim().length() == 0) {
            requestFocus(binding.editTextDateOfBirth);
            binding.editTextDateOfBirth.setError("Date of birth is required");
            return false;
        }
        if (binding.editTextNationality.getText().toString().trim().length() == 0) {
            requestFocus(binding.editTextNationality);
            binding.editTextNationality.setError("Nationality is required");
            return false;
        }
        if (binding.intendOfUse.getText().toString().trim().length() == 0) {
           requestFocus(binding.intendOfUse);
            binding.intendOfUse.setError("Intended use of account is required");
            return false;
        }
        if (binding.editTextMobileNumber.getText().toString().trim().length() == 0) {
            binding.editTextMobileNumber.requestFocus();
            binding.editTextMobileNumber.setError("Mobile number is required");
            return false;
        }
        if (binding.editTextPhoneNumber.getText().toString().trim().length() == 0) {
            binding.editTextPhoneNumber.requestFocus();
            binding.editTextPhoneNumber.setError("Phone number is required");
            return false;
        }
        if (binding.editTextGender.getText().toString().trim().length() == 0) {
            binding.editTextGender.requestFocus();
            binding.editTextGender.setError("Gender is required");
            return false;
        }
        if (!cardName.toLowerCase().contains("my") && binding.editTextDateOfIssue.getText().toString().trim().length() == 0) {
            requestFocus(binding.editTextDateOfIssue);
            binding.editTextDateOfIssue.setError("Date of issue is required");
            return false;
        }
        if (!cardName.toLowerCase().contains("my") && !cardName.toLowerCase().contains("driv") && binding.editTextPeriodOfStay.getText().toString().trim().length() == 0) {
            requestFocus(binding.editTextPeriodOfStay);
            binding.editTextPeriodOfStay.setError("Period of stay is required");
            return false;
        }
        if (binding.editTextExpiryDate.getText().toString().trim().length() == 0) {
            requestFocus(binding.editTextExpiryDate);
            binding.editTextExpiryDate.setError("Expiry date is required");
            return false;
        }
        if (binding.editTextPrefecture.getText().toString().trim().length() == 0) {
            binding.editTextPostalCode.requestFocus();
            binding.editTextPostalCode.setError("Address is required. Search with postal code to get data");
            return false;
        }
        if (binding.editTextBuildingName.getEditText().getText().toString().trim().length() == 0) {
            binding.editTextBuildingName.requestFocus();
            binding.editTextBuildingName.setError("Building name is required. Search with postal code to get data");
            return false;
        }

        return true;
    }

    private void requestFocus(TextInputEditText editText) {
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.setFocusableInTouchMode(false);
        editText.clearFocus();
    }

}