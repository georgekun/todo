<script>
	export default {
		name: "v-main",
		components: {},
	};
</script>

<template>
	<div class="v-main">
		<div class="flex">
			<router-link :to="{name:'login'}" class="link">Авторизоваться</router-link>
			<router-link :to="{name:'registration'}" class="link">Зарегистрироваться</router-link>
		</div>
	</div>
</template>

<style scoped>
.flex{
display:flex; width:300px;margin:0 auto}
	.link {
		text-align:center;
		border: 1px solid black;
		background-color: black;
		color: white;
		padding: 10px 15px;

		margin:10px;
		text-decoration:none;
	}
	.link:hover{
			transition:0.2s;
			background-color: white;
			color:black;
		}


	
</style>