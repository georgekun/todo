<template>
	<div class="v-itemTask">
		<h2 class="date">{{ day }}</h2>
		<h3 class="date full_date">{{ full_date }}</h3>
		<vInput
			v-for="task in taskFilter"
			:key=task
			:name="task.description"
			:id=task.id
			:date=task.date
			:done = task.done
		></vInput>
	<p class="form">	<input
			type="text"
			v-model="task_description"
		>
		<button @click="save_new_task()">➕</button></p>
	</div>
</template>

<script>
	import { mapGetters,mapActions } from "vuex";
	import vInput from "./v-input.vue";
	import axios from "axios";
	export default {
		name: "ItemTask",
		components: { vInput },
		data() {
			return {
				task_description: "",
			};
		},
		computed: {
			...mapGetters(["get_all_task"]),
			taskFilter() {
				const filtered = this.get_all_task.filter(
					(task) => task.date === this.UTC
				);
				return filtered;
			},
		},
		props: {
			full_date: String,
			day: String,
			UTC: String,
		},

		methods: {
			...mapActions(['get_task_from_api']),
			save_new_task() {
		
				axios
					.post(
						"task/post",
						{
							data: this.UTC,
							description: this.task_description,
							done:false
						},
						{ withCredentials: true }
					)
					.then(() => {
							this.task_description="";
							this.get_task_from_api();
					})
					.catch((e) =>alert(e));

			},
		},
	};
</script>

<style scoped>
	.v-itemTask {
		min-width: 250px;
		/* box-shadow: 1px 1px 1px 2px rgba(0, 0, 0, 0.938); */
		border-left: 1px solid rgba(0, 0, 0, 0.2);
		padding: 0 10px;
		min-height: 300px;
	}
	h2 {
		margin: 7px;
	}
	h3 {
		font-size: 15px;
		margin: 5px 0 15px 0;
	}
input {
		width: 80%;
		font-size: 16px;
		border: 0;
		border-bottom: 1.5px solid rgba(128, 128, 128, 0.289);
	}
.form{display: flex;justify-content: space-between;}
button {
		padding: 0;
		font-size: 10px;
		border: 0;
		margin-left: 3px;
background-color: #ffff;
cursor:pointer
	}
</style>