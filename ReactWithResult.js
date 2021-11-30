/**
 * 渲染一个RN页面，并且接收Native传递过来的数据
 */

import React from 'react';
import type {Node} from 'react';
import {StyleSheet, Text, View, BackHandler, Button} from 'react-native';
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';

import {popReactNativeWithResult} from './src/nativeEventToRNModule';

function handlePress() {
  popReactNativeWithResult('back data from rn').catch(e => {
    console.log('e :>> ', e);
  });
}

function HomeScreen() {
  return (
    <View style={styles.box}>
      <Text>Home!</Text>
      <Button onPress={handlePress} title="退出并传递数据" />
    </View>
  );
}
function SettingScreen() {
  return (
    <View style={styles.box}>
      <Text>Settings!</Text>
    </View>
  );
}

const Stack = createNativeStackNavigator();

const ReactWithResult: () => Node = () => {
  return (
    <NavigationContainer>
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

export default ReactWithResult;
