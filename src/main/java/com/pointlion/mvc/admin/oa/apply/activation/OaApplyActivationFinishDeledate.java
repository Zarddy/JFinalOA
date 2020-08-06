package com.pointlion.mvc.admin.oa.apply.activation;

import com.pointlion.mvc.admin.oa.common.CommonFinishDeledate;
import com.pointlion.mvc.admin.oa.common.OAConstants;
import com.pointlion.mvc.common.model.OaApplyActivation;
import com.pointlion.mvc.common.utils.CryptoUtils;
import com.pointlion.plugin.flowable.FlowablePlugin;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;

public class OaApplyActivationFinishDeledate extends CommonFinishDeledate {

    public static final OaApplyActivationService service = OaApplyActivationService.me;

    @SuppressWarnings("rawtypes")
    @Override
    public void execute(DelegateExecution execution) {
        try {
            super.updateModelRecord(execution);

            // TODO 生成验证码
            ProcessInstance instance = FlowablePlugin.buildProcessEngine().getRuntimeService().createProcessInstanceQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();
            String id = instance.getBusinessKey();//业务主键

            OaApplyActivation oaApplyActivation = service.getById(id);
            encrypt(oaApplyActivation);

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void encrypt(OaApplyActivation activation) {
        // 将传入的设备SN码与有效日期连接，再进行加密
        String deadline = activation.getDeadline();
        deadline = deadline.replace("-", ",");
        // 数据格式为 IR3020156,2020,09,18
        String data = activation.getSnCode() + "," + deadline;
        String activationCode = CryptoUtils.encrypt(data); // 加密后的激活码

        activation.setActivationCode(activationCode);
        activation.update();
    }
}
