package ru.krasevent;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventFragment extends android.support.v4.app.Fragment {

    /** Вьюхи фрагмента */
    EditText name, note, geo, address, start, finish;
    CheckBox ispublic;

    Button mapbtn;
    TextView createbtn;
    ProgressBar progress;

    /** Конструктор с нагруженный логикой */
    public CreateEventFragment() {

    }

    /**
     * Метод отрабатывающий при инициализации фрагмента
     *
     * @param inflater - передаем для того чтобы можно было из layout(где описан фрагмент) сформировать view
     * @param container - непонятно зачем нужен, в методе не используется
     * @param savedInstanceState - анологично предидущему аргументу
     *
     * @return - возвращаем созданное из layout(fragment_login.xml) view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);

        /** ищем вьюхи на форме */
        name = (EditText) view.findViewById(R.id.view_name);
        note = (EditText) view.findViewById(R.id.view_note);
        geo = (EditText) view.findViewById(R.id.view_geo);
        address = (EditText) view.findViewById(R.id.view_address);
        start = (EditText) view.findViewById(R.id.view_start);
        finish = (EditText) view.findViewById(R.id.view_finish);

        ispublic = (CheckBox) view.findViewById(R.id.view_ispublic);

        mapbtn = (Button) view.findViewById(R.id.view_map_btn);

        progress = (ProgressBar) view.findViewById(R.id.view_progress);
        createbtn = (TextView) view.findViewById(R.id.view_create);


        mapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MapGetGEOActivity.class);
                startActivityForResult(intent, 1);

            }
        });


        /** Вешаем действие на клик по "кнопке" */
        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** скрываем клавиатуру, выключаем кнопки, показываем прогрес меняем цвета */
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                createbtn.setEnabled(false);
                createbtn.setBackgroundColor(getResources().getColor(R.color.text_edit_disable));
                progress.setVisibility(View.VISIBLE);
                progress.setEnabled(true);

                String ispulic_text ="";
                if(ispublic.isChecked())
                    ispulic_text = "true";
                else
                    ispulic_text = "false";

                /** готовим запрос к выполнению(формируем юрл) */
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String createQuery = Global.host + "EventAPI.php?act=CreateEvent&name=%1$s&note=%2$s&image=%3$s&geo=%4$s&address=%5$s&stime=%6$s&ftime=%7$s&ispublic=%8$s&category=%9$s&type=%10$s&user_api_key=%11$s";
                createQuery = String.format(createQuery,name.getText().toString(), note.getText().toString(),
                        "image", geo.getText().toString(), address.getText().toString(), start.getText().toString(),
                        finish.getText().toString(), ispulic_text, "category", "type", Global.APIkey.toString());

                Log.d(Global.logD, createQuery);

                /** вешаем обработчики на успешное выполнение запроса и на ошибку */
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, createQuery, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            if(response.has("error"))
                            {
                                Toast.makeText(getActivity().getApplicationContext(), response.getString("error"), Toast.LENGTH_SHORT).show();
                                Toast.makeText(getActivity().getApplicationContext(), "Выполните вход", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Intent intent = new Intent(getActivity(), MapActivity.class);
                                getActivity().startActivity(intent);
                            }
                        }
                        catch(org.json.JSONException e)
                        {
                            Log.d(Global.logD,"Не удалось распарсить JSON!");
                        }

                        /** включаем все обратно, прогресс убираем, цвета меняем */
                        createbtn.setEnabled(true);
                        createbtn.setBackgroundColor(getResources().getColor(R.color.text_edit_enable));
                        progress.setVisibility(View.INVISIBLE);
                        progress.setEnabled(false);


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(Global.logD, "Запрос не был выполнен!");
                    }
                });
                /** добавляем запрос в очередь и выполняем */
                queue.add(jsObjRequest);

            }
        });


        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        String dt = data.getStringExtra("geo");
        geo.setText(dt);
    }

}