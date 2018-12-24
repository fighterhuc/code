<template>
	<section>page6...
		<el-button type="primary" style="width:100%;" @click.native.prevent="test" :loading="logining">登录</el-button>
		<!--<el-button @click.native.prevent="handleReset2">重置</el-button>-->
		</el-form-item>
	</section>
</template>
<script>
    import { httpPost } from '../../../api/api';
    export default {
		methods:{
		    test:function(){
				httpPost("test","",this);
			}
		}
    }
</script>
