/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, {useEffect, useCallback} from 'react';
import type {Node} from 'react';
import {StyleSheet, Text, View, BackHandler} from 'react-native';
import {
  NavigationContainer,
  useNavigationContainerRef,
  useFocusEffect,
} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';

import {nativeEventEmitter} from './src/nativeEventEmitter';
import {getIntentData, popReactNative} from './src/nativeEventToRNModule';

function HomeScreen() {
  return (
    <View style={styles.box}>
      <Text>Home!</Text>
    </View>
  );
}
function SettingScreen() {
  useFocusEffect(
    useCallback(() => {
      const onBackPress = () => {
        // 是否返回Native Activity
        const isPopRN = true;
        if (isPopRN) {
          popReactNative()
            .then()
            .catch(e => console.log('e', e));
          return true;
        } else {
          // pop a screen
          return false;
        }
      };
      BackHandler.addEventListener('hardwareBackPress', onBackPress);
      return () =>
        BackHandler.removeEventListener('hardwareBackPress', onBackPress);
    }, []),
  );

  return (
    <View style={styles.box}>
      <Text>Settings!</Text>
    </View>
  );
}

const Stack = createNativeStackNavigator();

const App: () => Node = () => {
  const navigationRef = useNavigationContainerRef();

  useEffect(() => {
    const listener = nativeEventEmitter.addListener('push', msg => {
      console.log('msg :>> ', msg);
    });
    // useEffect里这2个方法没问题，不过getIntentData可以取消吗？？？
    getIntentData()
      .then(route => {
        navigationRef.navigate('Settings');
        console.log('route :>> ', route);
      })
      .catch(e => {
        console.log('e :>> ', e);
      });

    return () => {
      console.log('unmount');
      listener.remove();
    };
  });

  return (
    <NavigationContainer ref={navigationRef}>
      <Stack.Navigator>
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="Settings" component={SettingScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

const styles = StyleSheet.create({
  box: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});

export default App;
