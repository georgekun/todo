<template>
	<div>
		<p>
			<input
				type="text"
				v-model="description"
				:readonly="not_allow"
				:class="classes"
				@click="done_this_task()"
			>
			<button
				@click="update_icons()"
				:hidden="hidden_by_delete"
			>✏️</button>
			<button
				@click="update_task()"
				:hidden="hidden_by_save"
			>✔️</button>
			<button
				@click="delete_this_task()"
				:hidden="hidden_by_delete"
			>❌</button>
		</p>

	</div>
</template>

<script>
	import axios from "axios";
	import { mapActions } from "vuex";
	export default {
		name: "vInput",
		data() {
			return {
				description: this.name,
				hidden_by_delete: false,
				hidden_by_save: true,
				not_allow: true,

				classes: "",
			};
		},
		props: {
			name: String,
			id: Number,
			done: Boolean,
		},
		computed: {},
		methods: {
			...mapActions(["get_task_from_api"]),
			async delete_this_task() {
				axios
					.post("task/delete", { id: this.id }, { withCredentials: true })
					.then(() => this.get_task_from_api())
					.catch(() => alert("Ошибка сервера, попробуйте позже"));
			},

			update_icons() {
				this.not_allow = false;
				this.hidden_by_delete = true;
				this.hidden_by_save = false;
				this.classes = "not_cursor";
			},

			async update_task() {
				this.classes = "";
				this.hidden_by_delete = false;
				this.hidden_by_save = true;
				this.not_allow = true;

				axios
					.post(
						"task/update",
						{ id: this.id, description: this.description },
						{ withCredentials: true }
					)
					.then(() => this.get_task_from_api())
					.catch(() => alert("Ошибка сервера, попробуйте позже"));
			},
			done_this_task() {
				//this.taskIsDone();
			if(this.not_allow==true){
				axios
					.post("task/done", { id: this.id }, { withCredentials: true })
					.then(() => this.get_task_from_api())
					.catch(() => alert("Ошибка сервера, попробуйте позже"));}
			},
			//присваиваем классс зачеркиванием нажатием на input
	
			if_task_done() {
				if (this.done === true) {
					this.classes = "strike";
				}
			},
		},

		beforeMount() {
			this.if_task_done();
		},
	};
</script>

<style scoped>
	input {
		cursor: pointer;
		width: 80%;
		font-size: 16px;
		border: 0;
		border-bottom: 1.5px solid rgba(128, 128, 128, 0.289);
	}

	button {
		padding: 0;
		font-size: 10px;
		border: 0;
		margin-left: 3px;
		background-color: #ffff;
		cursor: pointer;
	}

	/**класс зачеркивание */
	.strike {
		text-decoration: line-through;
		color: darkgrey;
	}
	.not_cursor {
		cursor: text;
	}
	p {
		display: flex;
		justify-content: space-between;
	}
</style>