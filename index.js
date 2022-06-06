import { NativeModules, Platform } from 'react-native';

const DetectModule = NativeModules.RNDetectSoftwareNavigationBar;

function checkForDetectModule() {
  if (!DetectModule) {
    throw "RNDetectSoftwareNavigationBar not defined. Try rebuilding your project. e.g. react-native run-android";
  }
}

export function get(dim) {
  if (Platform.OS !== 'android') {
    return 0;
  } else {
    try {
      checkForDetectModule();
      const result = DetectModule[dim];
      
      return result;
    } catch (e) {
      console.error(e);
    }
  }
}

export function getRealWindowHeight() {
  checkForDetectModule();
  return DetectModule.getRealHeight();
}

export function getRealWindowWidth() {
  checkForDetectModule();
  return DetectModule.getRealWidth();
}

export function getStatusBarHeight() {
  checkForDetectModule();
  return DetectModule.getStatusBarHeight();
}

export function getSoftMenuBarHeight() {
  checkForDetectModule();
  return DetectModule.getSoftMenuBarHeight();
}

export function getSmartBarHeight() {
  checkForDetectModule();
  return DetectModule.getSmartBarHeight();
}

export function isSoftMenuBarEnabled() {
  return get('SOFT_MENU_BAR_ENABLED');
}

export function isEmulator() {
  return get('IS_EMULATOR');
}

export function setNavBackgroundColor(color = '#ffffff') {
  checkForDetectModule();
  DetectModule.setNavBackgroundColor(color);
}

// stay compatible with pre-es6 exports
export default {
  get,
  getRealWindowHeight,
  getRealWindowWidth,
  getStatusBarHeight,
  getSoftMenuBarHeight,
  getSmartBarHeight,
  isSoftMenuBarEnabled,
  isEmulator,
  setNavBackgroundColor,
}