import { NativeModules } from 'react-native';

const { RNDetectSoftwareNavigationBar } = NativeModules;

const isSoftWare = async () => {
  return await RNDetectSoftwareNavigationBar.isSoftware();
}

export default isSoftWare().then(isEnabled => isEnabled).catch(() => false);
