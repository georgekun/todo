<template>
	<div class="v-form">
		
		<form
			action=""
			method="post"
			@submit.prevent="login"
		>
			<h3>Войти</h3>
			<input
				type="text"
				v-model="email"
				placeholder="login"
			>
			<input
				type="password"
				v-model="password"
				placeholder="password"
			>
			<button type="submit">Войти</button>
			<p class="cont_link"><router-link :to="{name:'registration'}" class="link">Зарегистрироваться</router-link></p>
		</form>
	</div>
</template>

<script>
import axios from "axios";
import { mapMutations } from 'vuex';


	export default {
		name: "v-form",
	
		data() {
			return {
				email: "",
				password: "",
			};
		},

		methods: {
			...mapMutations(['set_accessToken','set_refreshToken']),
			setAccessToken(token){this.set_accessToken(token)},

			
			login() {

					axios.post("login",{email: this.email,password: this.password,},{ withCredentials: true })
					.then((response) => {
							// vuex
						this.setAccessToken(response.data.access);
						//поптыаемся по дефолту задать токен авторизации
						axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.access}`;
						this.$router.push("/profile")
						//console.log(response.data.token);
					});
				
			},
	}};
</script>

<style scoped >
.cont_link{
font-size:14px;

}
.link{
color:rgb(43, 89, 207);text-decoration: none;
}
	form {
		margin: 0 auto;
		width: 200px;
		border-radius: 5px;
		background-color: #e4e1e18e;
		padding: 40px;
	}
	input {
		width: 90%;
		border-radius: 10px;
		padding: 8px;
		margin-bottom: 10px;
	}
	button {
cursor:pointer;
		border: 2px solid black;
		color:white;
		background-color: black;
		padding: 7px 20px;
		text-align: center;
		border-radius: 5px;
		margin: 0 auto;
		width: 100%;
	}
</style>