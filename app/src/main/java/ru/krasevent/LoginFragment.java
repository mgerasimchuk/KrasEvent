package ru.krasevent;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
 * TODO Добавить TabHost с вкоадкой регистрации пользователя
 * TODO Реализовать вход через соцсети
 */

/**
 * Фрагмент для авторизации пользователя в системе
 */
public class LoginFragment extends android.support.v4.app.Fragment {

    /** Вьюхи фрагмента */
    EditText login, pass;
    TextView loginBtn;
    ProgressBar progress;

    /** Конструктор с нагруженный логикой */
    public LoginFragment() {

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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        /** ищем вьюхи на форме */
        login = (EditText) view.findViewById(R.id.view_login);
        pass = (EditText) view.findViewById(R.id.view_password);
        progress = (ProgressBar) view.findViewById(R.id.view_progress);
        loginBtn = (TextView) view.findViewById(R.id.view_submit);

        /** Вешаем действие на клик по "кнопке" */
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** скрываем клавиатуру, выключаем кнопки, показываем прогрес меняем цвета */
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                loginBtn.setEnabled(false);
                loginBtn.setBackgroundColor(getResources().getColor(R.color.text_edit_disable));
                progress.setVisibility(View.VISIBLE);
                progress.setEnabled(true);

                /** готовим запрос к выполнению(формируем юрл) */
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String loginQuery = Global.host + "UserAPI.php?act=LoginUser&login=%1$s&pass=%2$s";
                loginQuery = String.format(loginQuery,login.getText().toString(), pass.getText().toString());

                Log.d(Global.logD, loginQuery);

                /** вешаем обработчики на успешное выполнение запроса и на ошибку */
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, loginQuery, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            if(response.has("user_api_key"))
                            {
                                Global.isLogin = true;
                                Global.APIkey = response.getString("user_api_key");
                            }

                            if(response.has("error"))
                            {
                                Toast.makeText(getActivity().getApplicationContext(), response.getString("error"),  Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch(org.json.JSONException e)
                        {
                            Log.d(Global.logD,"Не удалось распарсить JSON!");
                        }

                        /** включаем все обратно, прогресс убираем, цвета меняем */
                        loginBtn.setEnabled(true);
                        loginBtn.setBackgroundColor(getResources().getColor(R.color.text_edit_enable));
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

}
