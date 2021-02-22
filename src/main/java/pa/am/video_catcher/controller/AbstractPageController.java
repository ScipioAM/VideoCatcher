package pa.am.video_catcher.controller;

import com.jfoenix.controls.JFXProgressBar;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import pa.am.scipioutils.common.StringUtil;
import pa.am.scipioutils.jfoenix.ProgressDialog;
import pa.am.scipioutils.jfoenix.fxml.BaseController;
import pa.am.scipioutils.jfoenix.snackbar.JFXSnackbarHelper;

import java.io.File;
import java.util.concurrent.ExecutorService;

/**
 * @author Alan Min
 * @since 2021/2/18
 */
public abstract class AbstractPageController extends BaseController {

    protected ExecutorService threadPool;
    protected MainController mainController;
    protected StackPane rootPane;

    //=========================================================================

    /**
     * 必填项检查
     * @param url 下载的url
     * @param downloadDir 下载目录
     * @return 返回true代表非法
     */
    protected boolean isInvalidInputs(String url, File downloadDir) {
        if(StringUtil.isNull(url)) {
            JFXSnackbarHelper.showWarn(rootPane,"下载链接不能为空!");
            return true;
        }
        if(!StringUtil.isHttpUrl(url)) {
            JFXSnackbarHelper.showWarn(rootPane,"请输入合法的下载链接!");
            return true;
        }
        if(downloadDir==null) {
            JFXSnackbarHelper.showWarn(rootPane,"请选择下载目录!");
            return true;
        }

        return false;
    }

    /**
     * 界面控件与下载线程绑定
     */
    protected void bindTask2Progress(Task<String> task, Label label, JFXProgressBar progressBar) {
        label.textProperty().bind(task.messageProperty());
        progressBar.progressProperty().bind(task.progressProperty());
    }

    protected void bindTask2Progress(Task<String> task, ProgressDialog progressDialog) {
        progressDialog.getSpinner().progressProperty().bind(task.progressProperty());
        progressDialog.getContentLabel().textProperty().bind(task.messageProperty());
    }

    /**
     * 界面控件与下载线程解绑
     */
    public void unbindTask2Progress(Label label, JFXProgressBar progressBar) {
        label.textProperty().unbind();
        progressBar.progressProperty().unbind();
    }

    public void unbindTask2Progress(ProgressDialog progressDialog) {
        progressDialog.getSpinner().progressProperty().unbind();
        progressDialog.getContentLabel().textProperty().unbind();
    }

    //=========================================================================

    public void setThreadPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setRootPane(StackPane rootPane) {
        this.rootPane = rootPane;
    }

    public StackPane getRootPane() {
        return rootPane;
    }

}
