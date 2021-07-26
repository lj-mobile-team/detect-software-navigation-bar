import { NativeModules, Platform } from 'react-native';

const DectectModule = NativeModules.RNDetectSoftwareNavigationBar;

export function get(dim) {
  if (Platform.OS !== 'android') {
    return 0;
  } else {
    try {
      if (!DectectModule) {
        throw "RNDetectSoftwareNavigationBar not defined. Try rebuilding your project. e.g. react-native run-android";
      }
      const result = DectectModule[dim];

      if (typeof result !== 'number') {
        return result;
      }
      return result;
    } catch (e) {
      console.error(e);
    }
  }
}

export function getRealWindowHeight() {
  return get('REAL_WINDOW_HEIGHT');
}

export function getRealWindowWidth() {
  return get('REAL_WINDOW_WIDTH');
}

export function getStatusBarHeight() {
  return get('STATUS_BAR_HEIGHT');
}

export function getSoftMenuBarHeight() {
  return get('SOFT_MENU_BAR_HEIGHT');
}

export function getSmartBarHeight() {
  return get('SMART_BAR_HEIGHT');
}

export function isSoftMenuBarEnabled() {
  return get('SOFT_MENU_BAR_ENABLED');
}

export function isEmulator() {
  return get('IS_EMULATOR');
}

export function setNavBackgroundColor(color = '#ffffff') {
  if (!DectectModule) {
    throw "RNDetectSoftwareNavigationBar not defined. Try rebuilding your project. e.g. react-native run-android";
  }

  DectectModule.setNavBackgroundColor(color);
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
}