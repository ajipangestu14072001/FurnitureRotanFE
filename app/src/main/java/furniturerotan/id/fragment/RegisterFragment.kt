package furniturerotan.id.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import furniturerotan.id.databinding.FragmentRegisterBinding
import furniturerotan.id.helper.SharedPrefManager
import furniturerotan.id.home.HomeActivity
import furniturerotan.id.model.Login
import furniturerotan.id.response.ResponseLogin
import furniturerotan.id.response.ResponseRegister
import furniturerotan.id.services.APIClient
import furniturerotan.id.services.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterFragment : Fragment() {
private var  binding : FragmentRegisterBinding? = null
    var sharedPrefManager: SharedPrefManager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        sharedPrefManager = SharedPrefManager(requireContext())
        binding!!.btnRegister.setOnClickListener {
            if (validatePassword() && validateUsername()) {
                val register = ResponseRegister(binding!!.mail.text.toString(),binding!!.mail.text.toString(), binding!!.pwd.text.toString(), binding!!.name.text.toString(), binding!!.phone.text.toString())
                val apiInterface =
                    APIClient().getClient(requireContext()).create(APIInterface::class.java)
                apiInterface.addUser(register)
                    ?.enqueue(object : Callback<ResponseRegister?> {
                        override fun onResponse(
                            call: Call<ResponseRegister?>,
                            response: Response<ResponseRegister?>
                        ) {
                            if (response.body() != null) {
                                val login = Login(binding!!.mail.text.toString(), binding!!.pwd.text.toString())
                                val call: Call<ResponseLogin?>? = apiInterface.getUser(login)
                                call?.enqueue(object : Callback<ResponseLogin?> {
                                    override fun onResponse(
                                        call: Call<ResponseLogin?>,
                                        response: Response<ResponseLogin?>
                                    ) {

                                        Log.d("TAG", response.code().toString() + "")
                                        if (response.body()?.token != null) {
                                            val resource: ResponseLogin? = response.body()
                                            Toast.makeText(
                                                requireContext(),
                                                "Login successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            sharedPrefManager!!.saveSPBoolean(
                                                SharedPrefManager.SP_SUDAH_LOGIN,
                                                true
                                            )
                                            val intent = Intent(requireContext(), HomeActivity::class.java)
                                            val sharedPref = activity?.getSharedPreferences("myKey",
                                                Context.MODE_PRIVATE
                                            )
                                            val editor = sharedPref?.edit()
                                            editor?.putString("username", resource?.username)
                                            editor?.putString("userId", resource?.id)
                                            editor?.putString("token", resource?.token)
                                            editor?.putString("email", resource?.email)
                                            editor?.putString("telepon", resource?.telepon)
                                            editor?.putString("nama", resource?.nama)
                                            editor?.apply()
                                            startActivity(intent)
                                            activity?.finish()
                                        }else{
                                            Toast.makeText(
                                                requireContext(),
                                                "Invalid credentials",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<ResponseLogin?>, t: Throwable) {
                                        Toast.makeText(
                                            requireContext(),
                                            "Invalid credentials",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        call.cancel()
                                    }
                                })
                            } else {
                                Toast.makeText(
                                    activity,
                                    "Terjadi Kesalahan, Coba Lagi Nanti",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseRegister?>, t: Throwable) {}
                    })
            }
        }

        return binding!!.root
    }

    private fun validateUsername(): Boolean {
        if (TextUtils.isEmpty(binding!!.mail.text.toString())) {
            binding!!.mail.error = "username cannot be empty"
            binding!!.mail.requestFocus()
            return false
        }
        return true
    }

    private fun validatePassword(): Boolean {
        if (TextUtils.isEmpty(binding!!.pwd.text.toString())) {
            binding!!.pwd.error = "password cannot be empty"
            binding!!.pwd.requestFocus()
            return false
        }
        return true
    }
}