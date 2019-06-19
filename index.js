import { NativeModules } from 'react-native';

const { RNDetectSoftwareNavigationBar } = NativeModules;

const callbacl = (bool) => {
  console.log('bool', bool);
}

const isSoftware = RNDetectSoftwareNavigationBar.isSoftware(callbacl);

export default isSoftware;
