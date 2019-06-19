import { NativeModules } from 'react-native';

const { RNDetectSoftwareNavigationBar } = NativeModules;

const isSoftware = async () => { 
  const temp = await RNDetectSoftwareNavigationBar.isSoftware();
  console.log('temp', temp);
}

export default isSoftware;
