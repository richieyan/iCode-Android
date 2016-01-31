package com.rayfantasy.icode.ui.activity


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.android.volley.Request
import com.rayfantasy.icode.R
import com.rayfantasy.icode.extension.snackBar
import com.rayfantasy.icode.postutil.PostUtil
import com.rayfantasy.icode.postutil.extension.e
import kotlinx.android.synthetic.main.content_login.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity

class LoginActivity : ActivityBase() {
    private var request: Request<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        /*
                register.setOnClickListener { startActivity<RegisterActivity>() }
                fab.setOnClickListener {
                    if (FormValidator.validate(this, TextInputLayoutCallback(this))) {
                        val username = etUsername.text.toString()
                        val password = etUsername.text.toString()
                        postUtil?.loginUser(username, password, {

                            Toast.makeText(this@LoginActivity, "欢迎回来$username", Toast.LENGTH_SHORT).show()
                            finish()

                        }, { t, rc ->
                            t.printStackTrace()
                            Toast.makeText(this@LoginActivity, "用户${username}登陆失败", Toast.LENGTH_SHORT).show()
                        })
                    }
                }
            }*/
        login_fab.onClick {
            request = PostUtil.loginUser(
                    login_et_username.text.toString(),
                    login_et_password.text.toString(),
                    onSuccess = {
                        loginSucceed()
                        request = null
                    },
                    onFailed = { t, rc ->
                        e("failed, rc =  $rc")
                        /*throw RuntimeException("$rc");*/
                        login_fab.snackBar("欢迎回来登陆失败，错误代码:$rc" , Snackbar.LENGTH_LONG)
                        request = null
                    }
            )

        }
        register_button.onClick {
            startActivity<RegisterActivity>()
        }
        /*login_tv_register.onClick {
            startActivity<RegisterActivity>()
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        request?.let {
            PostUtil.cancel(request)
            request = null
        }
    }

    fun loginSucceed() {
        login_fab.snackBar("欢迎回来" + PostUtil.user?.username, Snackbar.LENGTH_LONG)
        finish()
    }
}
