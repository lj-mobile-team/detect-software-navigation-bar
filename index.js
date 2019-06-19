import { NativeModules } from 'react-native';

const { RNDetectSoftwareNavigationBar } = NativeModules;

const isSoftware = async () => { 
  return await RNDetectSoftwareNavigationBar.isSoftware(); 
}

console.log('after', isSoftware());

export default isSoftware;
