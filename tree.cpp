#include <iostream> // cout, endl
#include <fstream>  // fstream
#include <vector>
#include <string>
#include <algorithm> // copy
#include <iterator>  // ostream_operator
#include <boost/tokenizer.hpp>

struct Stack
{
    std::string current;
    std::vector<std::string> path;
};

int main()
{
    using namespace std;
    using namespace boost;
    string data("CONJUNTOS.csv");

    ifstream in(data.c_str());
    if (!in.is_open())
        return 1;

    typedef tokenizer<escaped_list_separator<char>> Tokenizer;
    vector<string> vec;
    vector<vector<string>> list;
    string line;

    while (getline(in, line))
    {
        Tokenizer tok(line);
        vec.assign(tok.begin(), tok.end());
        list.push_back(vec);
    }

    Stack s{};
    s.current = "00130160";
    s.path = {};

    vector<Stack> stack;
    vector<vector<string>> root;
    stack.push_back(s);

    while (stack.size() > 0)
    {
        // cout << "Stack size: " << stack.size() << endl;
        Stack cur = stack.back();
        stack.pop_back();

        // cout << "Current stack: " << cur.current << endl;

        vector<Stack> founded;

        for (size_t i = 0; i < list.size(); i++)
        {
            auto row = list[i];

            if (row[0] == cur.current)
            {
                if (cur.path.size() == 0)
                {
                    Stack r;
                    r.current = row[1];
                    r.path = {cur.current, row[1]};
                    founded.push_back(r);
                }
                else
                {
                    Stack r;
                    r.path.assign(cur.path.begin(), cur.path.end());
                    r.path.push_back(row[1]);
                    r.current = row[1];
                    founded.push_back(r);
                }
            }
        }

        if (founded.size() > 0)
        {
            for (size_t i = 0; i < founded.size(); i++)
            {
                string paths;
                vector<string> p = founded[i].path;
                for (size_t j = 0; j < p.size(); j++)
                {
                    paths += p[j];
                    if (j < p.size() - 1)
                    {
                        paths += "-";
                    }
                }
                vector<string> r;
                r.push_back(founded[i].current);
                r.push_back(paths);
                root.push_back(r);

                // cout << "founded: " << founded[i].current << endl;
            }

            stack.insert(stack.end(), founded.begin(), founded.end());
        }
    }

    std::sort(root.begin(), root.end(),
              [](const std::vector<string> &a, const std::vector<string> &b)
              {
                  return a[1] < b[1];
              });

    for (size_t i = 0; i < root.size(); i++)
    {
        // copy(root[i].begin(), root[i].end(), ostream_iterator<string>(cout, ","));
        cout << "[" << root[i][0] << ", " << root[i][1] << "]" << endl;
    }
}