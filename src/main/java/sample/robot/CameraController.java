package sample.robot;

import java.io.IOException;
import java.util.Map;

import jp.vstone.camera.CameraCapture;

public class CameraController implements Controller {

  @Override
  public void executeAction(String action, Map<String, String> data) {
    CameraCapture cameraCapture = null;
    cameraCapture = new CameraCapture(CameraCapture.CAP_IMAGE_SIZE_VGA, CameraCapture.CAP_FORMAT_BYTE_GRAY);

    try {
      cameraCapture.openDevice("/dev/video"); // for telenoid: /dev/video0
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

  }

}
