#include <iostream>
#include <stdio.h>
#include <fstream>
#include <vector>
#include <string.h>
#include <string>
using namespace std;
string result = "Rejected";
struct transition
{
    char from;
    char symbol;
    vector<char> to;
};

bool inFinal(vector<char> finalStates, char target)
{
    for (int i = 0; i < finalStates.size(); i++)
    {
        if (target == finalStates[i])
            return true;
    }
    return false;
}

int searchTransitions(char current, char inputString, vector<transition> &transitionTable)
{
    for (int i = 0; i < transitionTable.size(); i++)
    {
        if (transitionTable[i].from == current && transitionTable[i].symbol == inputString)
        {
            return i;
        }
    }
    return -1;
}

void find(char currentState, char inputString[], vector<char> finalStates, vector<transition> &transitionTable)
{
    int pos = searchTransitions(currentState, inputString[0], transitionTable);
    cout << currentState << " " << inputString[0] << " ";

    if (inputString[0] == '\0' && inFinal(finalStates, currentState))
    {
        cout << "  v" << endl;
        result = "Accepted";
        return;
    }

    if (inputString[0] == '\0')
    {
        cout << "  x" << endl;
        return;
    }

    else if (pos == -1)
    {
        cout << "  x" << endl;
        return;
    }

    for (int i = 0; i < transitionTable[pos].to.size(); i++)
        find(transitionTable[pos].to[i], inputString + 1, finalStates, transitionTable);
}

vector<char> extract(string &s)
{
    vector<char> result;
    for (int i = 0; i < s.length(); i++)
    {
        if (s[i] == ',')
        {
            continue;
        }
        else
        {
            result.push_back(s[i]);
        }
    }
    return result;
}

void readNFA(string fileName, int &nInputs, int &nStates, vector<char> &inputs, char &initialState, vector<char> &finalStates, vector<transition> &transitionTable)
{
    ifstream file(fileName);
    file >> nStates;
    string sym;
    file >> sym;
    inputs = extract(sym);
    file >> nStates;
    file >> initialState;
    file >> sym;
    finalStates = extract(sym);
    string transitionIterator;
    while (file >> transitionIterator)
    {
        transition temp;
        temp.from = transitionIterator[0];
        temp.symbol = transitionIterator[2];
        string s = transitionIterator.substr(4);
        temp.to = extract(s);
        transitionTable.push_back(temp);
    }

    cout << "\n -- Specified NFA --" << endl;
    cout << "Inputs : ";
    cout << inputs[0];
    for (int i = 1; i < inputs.size(); i++)
        cout << " , " << inputs[i];

    cout << "\nInitial State : " << initialState << "\nFinal States : ";
    cout << finalStates[0];
    for (int i = 1; i < finalStates.size(); i++)
        cout << " , " << finalStates[i];

    cout << "\nTransitions : \n";
    for (int i = 0; i < transitionTable.size(); i++)
    {
        cout << "\td(" << transitionTable[i].from << "," << transitionTable[i].symbol << ") = ";
        cout << transitionTable[i].to[0];
        for (int j = 1; j < transitionTable[i].to.size(); j++)
            cout << " , " << transitionTable[i].to[j];
        cout << endl;
    }
}

int main()
{
    int nInputs, nStates;
    vector<char> inputs, finalStates;
    char initialState;
    vector<transition> transitionTable;
    readNFA("specs.txt", nInputs, nStates, inputs, initialState, finalStates, transitionTable);
    cout << "\nEnter The String : ";
    char str[100];
    scanf("%[^\n]s", str);
    find(initialState, str, finalStates, transitionTable);
    cout << "Given string " << str << " is " << result;
    return 0;
}