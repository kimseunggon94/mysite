package kr.co.itcen.mysite.action.board;

import kr.co.itcen.web.mvc.Action;
import kr.co.itcen.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action =null;
		if("writeform".equals(actionName)) {
			action = new WriteformAction();
		}else if("write".equals(actionName)) {
			action = new WriteAction();
		}else if("view".equals(actionName)) {
			action = new ViewAction();
		}else if("modifyform".equals(actionName)) {
			action = new ModifyformAction();
		}else if("modify".equals(actionName)) {
			action = new ModifyAction();
		}else if("delete".equals(actionName)) {
			action = new DeleteAction();
		}else {
			/* deafult */
			action = new ListAction();
		}
		
		return action;
	}

}
