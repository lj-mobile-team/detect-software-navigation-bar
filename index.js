import { NativeModules } from 'react-native';

const { RNDetectSoftwareNavigationBar } = NativeModules;

const isSoftware = async () => {
  return await RNDetectSoftwareNavigationBar.isSoftware();
}

export default isSoftware().then(isEnabled => isEnabled).catch(() => false);
