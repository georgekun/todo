<template>
	<div class="v-regist">
		<form
			action=""
			method="post"
			@submit.prevent="registration"
		>
			<h3>Зарегистрироваться</h3>
	<p class="warn_text" :hidden="hidden">{{warn_text}}!</p>
			<input
				type="text"
				v-model="name"
				placeholder="first name"
			>
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
			<button>Зарегистрироваться</button>
		
			<p class="cont_link"><router-link
					:to="{name:'login'}"
					class="link"
				>У меня уже есть аккаунт</router-link></p>
		</form>
	</div>
</template>

<script>
	import axios from "axios";

	export default {
		name: "v-regist",
		data() {
			return {
				name: "",
				email: "",
				password: "",
				hidden: true,
				warn_text:""
			};
		},

		methods: {
			is_valid() {
				if (this.name.length < 2) {
					this.warn_text="слишком короткое имя"
					return false;
				} else if (this.email.indexOf("@")==-1) {
	this.warn_text="Некоректный почтовый адрес"
					return false;
				} else if (this.password.length < 8) {
	this.warn_text="Длинна пароля должна состовлять не менее 8 символов"
					return false;
				}

				return true;
			},

			registration() {
				if (this.is_valid()) {
					axios
						.post(
							"registration",
							{
								name: this.name,
								email: this.email,
								password: this.password,
							},
							{ withCredentials: true }
						)
						.then((response) => {
							if (
								response.data ===
								"Данный E-mail уже зарегистрирован!"
							) {
								alert("Пользователь с таким E-mail уже существует");
							}
							if (response.data === "Пользователь зарегистрирован!") {
								this.$router.push("/login");
							}
						});
				} else {
					this.hidden = false;
				}
			},
		},
	};
</script>

<style scoped>
.v-regist{
		height:350px;
}
	.warn_text{
		color:red;
		font-size:12px;
}
	.cont_link {
		padding: 10px;
		font-size: 14px;
		margin-top: 20px;
	}
	.link {
		color: rgb(43, 89, 207);
		text-decoration: none;
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