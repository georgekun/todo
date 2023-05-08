<template>
	<div class="v-profile">
		<!-- <h3>Id = {{ data.id }}</h3>
		<h3>Имя - {{ data.name}}</h3>
		<button @click="getName()">getName</button>
		<button @click="logout()">Выйти</button> -->
		<div class="info">
			<h2 class="name">Hi, {{ info.name }}!</h2>

			<button @click="logout()">Выйти</button>
		</div>
		<div class="scroll_bar">
			<div
				class="arrow"
				id="i1"
				@click="scroll_left()"
			></div>
			<div
				class="arrow"
				id="i2"
				@click="scroll_right()"
			></div>
		</div>
		<div
			class="container"
			ref="scroll_x"
		>
			<item-task
				v-for="element in dateAndDay"
				:key="element.id"
				:day=element.days
				:full_date=element.full_date
				:UTC=element.date_for_backend
			></item-task>
		</div>
	</div>
</template>

<script>
	import ItemTask from "./v-itemTask.vue";
	import axios from "axios";
	import { mapActions } from "vuex";
	export default {
		name: "v-profile",
		components: {
			ItemTask,
		},
		data() {
			return {
				info: {
					name: "",
					id: "",
				},
				taskName: "",
				dataTime: "",
				dateAndDay: [],
			};
		},

		computed: {},

		methods: {
			//импортирую функцию из vuex в компонент
			...mapActions(["get_task_from_api"]),

			calculateDate() {
				let all_date = [];
				var days_of_week = [
					"Воскресенье",
					"Понедельник",
					"Вторник",
					"Среда",
					"Четверг",
					"Пятница",
					"Суббота",
				];

				for (let i = 0; i < 7; i++) {
					var full = new Date();
					var new_date = new Date();
					new_date.setDate(full.getDate() + i);

					let object_date = {
						full_date: new_date.toLocaleDateString(),
						date_for_backend: new_date.toISOString().substring(0, 10), //сделаю еще utc так как в следующем компоненте нужен формат UTC
						days: days_of_week[new_date.getDay()],
					};

					all_date.push(object_date);
				}
				this.dateAndDay = all_date;
			},

			download() {
				axios
					.get("user")
					.then((response) => {
						this.info.name = response.data.name;
						this.info.id = response.data.id;
					})
					.catch(() => {
						this.data.name =
							"НоуНейм, иди поспи блять, потом авторизуйся";
					});
				this.calculateDate();
			},

			logout() {
				axios
					.post("logout", {}, { withCredentials: true })
					.then(() => {
						this.$router.push("/");
					})
					.catch((e) => alert(e));
			},

			scroll_right() {
				let i = setInterval(() => {
					this.$refs.scroll_x.scrollLeft += 5;
				}, 10);

				setTimeout(function () {
					clearInterval(i);
				}, 1000);
			},
			scroll_left() {
				let i = setInterval(() => {
					this.$refs.scroll_x.scrollLeft -= 5;
				}, 10);

				setTimeout(function () {
					clearInterval(i);
				}, 1000);
			},
		},
		//при загрузке компонента вызову функции
		beforeMount() {
			this.get_task_from_api();
			this.download();
		},
	};
</script>

<style scoped>
	.scroll_bar {
		display: flex;
		justify-content: space-between;
	}
	.arrow {
		width: 50px;
		height: 50px;
		background-size: cover;
		background-repeat: no-repeat;
		margin-bottom: 30px;
		cursor: pointer;
	}
	#i1 {
		background-image: url("@/assets/img/left.png");
	}
	#i2 {
		background-image: url("@/assets/img/right.png");
	}
	.v-profile {
		padding: 0 15px;
		margin: 0 auto;
	}
	.info {
		display: flex;
		justify-content: space-between;
		margin-bottom: 100px;
		padding: 0 25px;
	}
	.name {
		padding-bottom: 3px;
		border-bottom: 2px solid grey;
	}
	button {
		height: 50%;
		margin: auto 0;
		text-align: center;
		border: 2px solid black;
		color: rgb(27, 27, 27);
		padding: 7px 30px;
		font-size: 15px;
		text-decoration: none;
		background-color: #fff;
	}
	button:hover {
		cursor: pointer;
		transition: 0.2s;
		color: white;
		background-color: black;
	}
	.container {
		margin-top: 100px;
		display: flex;
		margin: 0 auto;
		justify-content: space-between;
		overflow:hidden;
		
	}
</style>