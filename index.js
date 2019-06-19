import { NativeModules } from 'react-native';

const { RNDetectSoftwareNavigationBar } = NativeModules;

const temp = await RNDetectSoftwareNavigationBar.isSoftware();

// const isSoftware = async () => { 
  
//   console.log('temp', temp);
//   return temp;
// }

console.log('after', temp);

export default isSoftware;
