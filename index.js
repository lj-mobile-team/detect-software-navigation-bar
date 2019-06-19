import { NativeModules } from 'react-native';

const { RNDetectSoftwareNavigationBar } = NativeModules;

const isSoftware = async () => { 
  const temp = await RNDetectSoftwareNavigationBar.isSoftware();
  console.log('temp', temp);
}

const getHeight = async () => {
  const height = await RNDetectSoftwareNavigationBar.getHeight();
  console.log('height', height);
}

export {
  isSoftware,
  getHeight,
};
