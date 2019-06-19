import { NativeModules } from 'react-native';

const { RNDetectSoftwareNavigationBar } = NativeModules;

const isSoftware = async () => { 
  const temp = await RNDetectSoftwareNavigationBar.isSoftware();
  console.log('temp', temp);
  return temp;
}

console.log('after', isSoftware());

export default isSoftware;
